package com.manulife.ap.jenkins

public class CommonMethods {
    static Script scriptObj

    CommonMethods(Script scriptObj) { this.scriptObj = scriptObj }

    public String getVersionSuffix() {
        if (params.RC) {
            return env.VERSION_RC
        } else {
            return env.VERSION_RC + '_ci.' + env.BUILD_NUMBER
        }
    }

    public void getAllToolVersions() {
        this.scriptObj.echo 'Get all tool versions...'
        this.scriptObj.sh 'git --version'
        this.scriptObj.sh 'dotnet --version'
    }
    
}