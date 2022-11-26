pipeline {
    agent none

    parameters {
        booleanParam(name: 'RC', defaultValue: false, description: 'Is this a release candidate?')
    }
    environment {
        VERSION =  "0.1.0"
        VERSION_RC = "rc.2"
        GBRANCH = "feature-branch-01"
    }

    stages {
        stage('Audit Tools') {
            steps {
                getAllToolVersions()
            }
        }
        stage('Build') {
            agent {
                docker { image 'alpine:latest' }
            }
            environment { VERSION_SUFFIX = getVersionSuffix() }
            steps {
                echo "Building version: ${VERSION} with suffix version ${VERSION_SUFFIX}"
                echo 'Collecting resources...'
                echo 'Building binaries...'

                echo 'Archiving all artifacts...'
                //archiveArtifacts 'full.bin'
            }
        }
        stage('Test') {
            parallel {
                stage('Module Test 1') {
                    agent {
                        docker { image 'artifactory.ap.manulife.com/docker/devops-ci-image:2.5.7' }
                    }
                    steps {
                        echo 'Running ModuleTest1...'
                        writeFile file: 'mt-test-results.txt', text: 'passed'
                        //archiveArtifacts '/etc/mt-test-results.txt'
                    }
                }
                stage('Unit Test 1') {                    
                    agent {
                        docker { image 'artifactory.ap.manulife.com/docker/devops-ci-image:2.5.7' }
                    }
                    steps {
                        echo 'Running Unit Test 1...'
                        writeFile file: 'ut-test-results.txt', text: 'passed'
                        //archiveArtifacts '/etc/ut-test-results.txt'
                    }
                }
            }
        }
        stage('Approved Prod Deploy') {
            input {
                message "Merge now?"
                ok 'Do it!'
                parameters {
                    string(name: "TARGET BRANCH", defaultValue: "${GIT_BRANCH}", description: "Target branch")
                }
            }
            steps {
                echo "Deploying commit to branch: ${GIT_BRANCH}" 
            }
            post {
                success {
                    echo "Integration approved!"
                    mail bcc: '', body: 'Change Commit approved and merged!!! \nPlease disregard! This is just a test.', cc: '', from: '', replyTo: '', subject: 'Test Mail using Jenkins Pipeline', to: 'Syd_Pachica@manulife.com'
                }
                aborted {
                    echo "Change Integration Denied"
                }
            }
        }
    }
}

String getVersionSuffix() {
    if (params.RC) {
        return env.VERSION_RC
    } else {
        return env.VERSION_RC + '_ci.' + env.BUILD_NUMBER
    }
}

void getAllToolVersions() {
    echo 'Get all tool versions...'
    echo 'git --version'
    echo 'java --version'
    echo 'docker --version'
}