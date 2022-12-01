package com.manulife.ap.jenkins

public class CommonMethods {

    public void getAllToolVersions() {
        echo 'Get all tool versions...'
        echo 'git --version'
        echo 'java --version'
        echo 'docker --version'
    }
    
}