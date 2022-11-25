pipeline {

    stages {
        stage('Build') {
            agent {
                docker {
                    image 'artifactory.ap.manulife.com/docker/devops-ci-image:2.5.7'
                args  '-u devops:docker --privileged -e PATH=/tech/nvm/versions/node/v11.9.0/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/tech/Fortify/bin/:/tech/apache-ant-1.9.14/bin -v /app/maven/.m2:/home/devops/.m2 -v /var/run/docker.sock:/var/run/docker.sock'
                }
            }
            steps {
                echo "Executing Build Number: $BUILD_NUMBER"
                echo 'Collecting resources...'
                echo 'Building binaries...'
                echo 'Archiving all artifacts...'
                // mail bcc: '', body: 'Please disregard! This is just a test.', cc: '', from: '', replyTo: '', subject: 'Test Mail using Jenkins Pipeline', to: 'Syd_Pachica@manulife.com'
            }
        }
        stage('Test') {
            parallel {
                stage('Module Test 1') {
                    agent {
                        docker {
                            image 'artifactory.ap.manulife.com/docker/devops-ci-image:2.5.7'
                        args  '-u devops:docker --privileged -e PATH=/tech/nvm/versions/node/v11.9.0/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/tech/Fortify/bin/:/tech/apache-ant-1.9.14/bin -v /app/maven/.m2:/home/devops/.m2 -v /var/run/docker.sock:/var/run/docker.sock'
                        }
                    }
                    steps {
                        echo 'Running ModuleTest1...'
                        sleep 2
                    }
                }
                stage('Unit Test 1') {
                    agent {
                        docker {
                            image 'artifactory.ap.manulife.com/docker/devops-ci-image:2.5.7'
                        args  '-u devops:docker --privileged -e PATH=/tech/nvm/versions/node/v11.9.0/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/tech/Fortify/bin/:/tech/apache-ant-1.9.14/bin -v /app/maven/.m2:/home/devops/.m2 -v /var/run/docker.sock:/var/run/docker.sock'
                        }
                    }
                    
                    steps {
                        echo 'Running ModuleTest2...'
                        sleep 2
                    }
                }
            }
        }
        stage('Approved Prod Deploy') {
            steps {
                input message: "Deploy?"
            }
            post {
                success {
                    echo "Production Deploy Approved"
                }
                aborted {
                    echo "Production Deploy Denied"
                }
            }
        }
    }
}