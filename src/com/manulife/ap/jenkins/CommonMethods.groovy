package com.manulife.ap.jenkins

public class CommonMethods {
    
    public String getVersionSuffix() {
        if (params.RC) {
            return env.VERSION_RC
        } else {
            return env.VERSION_RC + '_ci.' + env.BUILD_NUMBER
        }
    }

    public void getAllToolVersions() {
        echo 'Get all tool versions...'
        echo 'git --version'
        echo 'java --version'
        echo 'docker --version'
    }
    
}