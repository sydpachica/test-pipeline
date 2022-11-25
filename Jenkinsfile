pipeline {
    agent none

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
                post {
                    success {
                        archiveArtifacts 'ut-test-results.txt'
                        archiveArtifacts 'mt-test-results.txt'
                    }
                }
            }
        }
        stage('Approved Prod Deploy') {
            environment {
                TARGET_ENVIRONMENT = 'PROD'
            }
            input {
                message "Deploy?"
                ok 'Do it!'
                parameters {
                    string(name: 'TARGET ENVIRONMENT', defaultValue: 'PROD', description: 'Target deployment environmen')
                }
            }
            steps {
                echo "Deploying to environment ${TARGET_ENVIRONMENT}" 
            }
            post {
                success {
                    echo "Production Deploy Approved"
                    mail bcc: '', body: 'Production Deploy Approved!!! Please disregard! This is just a test.', cc: '', from: '', replyTo: '', subject: 'Test Mail using Jenkins Pipeline', to: 'Syd_Pachica@manulife.com'
                }
                aborted {
                    echo "Production Deploy Denied"
                }
            }
        }
    }
}