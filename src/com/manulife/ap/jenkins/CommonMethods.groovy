package com.manulife.ap.jenkins

public class CommonMethods {
    static Script scriptObj

    CommonMethods(Script scriptObj) { this.scriptObj = scriptObj }

    public void getAllToolVersions() {
        this.scriptObj.echo 'Get all tool versions...'
        this.scriptObj.sh 'git --version'
        this.scriptObj.sh 'dotnet --version'
    }
    
}