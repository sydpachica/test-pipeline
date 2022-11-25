pipeline {
    
    environment {
        G_BRANCH = 'feature-branch01'
    }

    stages {
        stage('Build') {
            agent {
                docker {
                    image 'artifactory.ap.manulife.com/docker/devops-ci-image:2.5.7'
                }
            }
            steps {
                echo "Executing Build Number: $BUILD_NUMBER"
                echo 'Collecting resources...'
                echo 'Building binaries...'
                echo 'Archiving all artifacts...'
            }
        }
        stage('Test') {
            parallel {
                stage('Module Test 1') {
                    agent {
                        docker {
                            image 'artifactory.ap.manulife.com/docker/devops-ci-image:2.5.7'
                        }
                    }
                    steps {
                        echo 'Running ModuleTest1...'
                        writeFile file: 'mt-test-results.txt', text: 'passed'
                    }
                }
                stage('Unit Test 1') {                    
                    agent {
                        docker {
                            image 'artifactory.ap.manulife.com/docker/devops-ci-image:2.5.7'
                        }
                    }
                    steps {
                        echo 'Running Unit Test 1...'
                        writeFile file: 'ut-test-results.txt', text: 'passed'
                    }
                }
            }
        }
        stage('Approved Prod Deploy') {
            input {
                message "Merge?"
                ok 'Approve!'
                parameters {
                    string(name: "TARGET BRANCH", defaultValue: "${G_BRANCH}", description: "Target branch")
                }
            }
            steps {
                echo "Deploying to commit ${G_BRANCH}" 
            }
            post {
                success {
                    echo "Change Integration to ${G_BRANCH} Approved!"
                    mail bcc: '', body: 'Change Commit approved and merged!!! \nPlease disregard! This is just a test.', cc: '', from: '', replyTo: '', subject: 'Test Mail using Jenkins Pipeline', to: 'Syd_Pachica@manulife.com'
                    archiveArtifacts 'ut-test-results.txt'
                    archiveArtifacts 'mt-test-results.txt'
                }
                aborted {
                    echo "Change Integration Denied"
                }
            }
        }
    }
}