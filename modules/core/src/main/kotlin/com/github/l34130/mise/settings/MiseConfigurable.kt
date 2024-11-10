package com.github.l34130.mise.settings

import com.github.l34130.mise.notifications.Notification
import com.github.l34130.mise.settings.ui.MiseConfigurationPanelEditor
import com.github.l34130.mise.settings.ui.MiseConfigurationPanel
import com.intellij.notification.NotificationType
import com.intellij.openapi.options.SearchableConfigurable
import javax.swing.JComponent

class MiseConfigurable : SearchableConfigurable {
    private var component: MiseConfigurationPanel? = null

    override fun createComponent(): JComponent {
        component = component ?: MiseConfigurationPanel().also {
            it.state = MiseSettings.instance.state
        }
        return component!!
    }

    override fun isModified(): Boolean {
        return component?.let {
            it.enableMiseCheckBox.isSelected != MiseSettings.instance.state.isMiseEnabled
                    || it.miseProfileField.text != MiseSettings.instance.state.miseProfile
        } ?: false
    }

    override fun apply() {
        Notification.notify("apply()", NotificationType.INFORMATION)

        component?.let {
            MiseSettings.instance.state.isMiseEnabled = it.enableMiseCheckBox.isSelected
            MiseSettings.instance.state.miseProfile = it.miseProfileField.text
        }
    }

    override fun reset() {
        component?.state = MiseSettings.instance.state
    }

    override fun disposeUIResources() {
        component = null
    }

    override fun getDisplayName(): String = MiseConfigurationPanelEditor.EDITOR_TITLE

    override fun getId(): String = MiseConfigurationPanelEditor.SERIALIZATION_ID
}
