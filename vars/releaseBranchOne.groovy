import com.manulife.ap.jenkins.CommonMethods

pipeline {
    agent none

    parameters {
        booleanParam(name: 'RC', defaultValue: false, description: 'Is this a release candidate?')
    }
    environment {
        VERSION =  "0.1.0"
        VERSION_RC = "rc.2"
    }

    stages {
        stage('Build') {
            agent {
                docker { image 'alpine:latest' }
            }

            environment { 
                script {
                    def commonMethods1 = new CommonMethods(this)
                    VERSION_SUFFIX = commonMethods1.getVersionSuffix()
                }
            }

            steps {
                script {
                    echo "Building version: ${VERSION} with suffix version ${VERSION_SUFFIX}"

                    def commonMethods2 = new CommonMethods(this)
                    commonMethods2.getAllToolVersions()
                }

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
                    string(name: "TARGET BRANCH", defaultValue: "master", description: "Target branch")
                }
            }
            steps {
                echo "Deploying commit to master branch." 
            }
            post {
                success {
                    echo "Integration approved!"
                    mail bcc: '', body: 'Change integration has been granted!! \nPlease disregard! This is just a test.', cc: '', from: '', replyTo: '', subject: 'Test Mail using Jenkins Pipeline', to: 'Syd_Pachica@manulife.com'
                }
                aborted {
                    echo "Change Integration Denied"
                    mail bcc: '', body: 'Change integration has been denied!!! \nPlease disregard! This is just a test.', cc: '', from: '', replyTo: '', subject: 'Test Mail using Jenkins Pipeline', to: 'Syd_Pachica@manulife.com'
                }
            }
        }
    }
}