# Documentation

## MiniKube

    Single Node Cluster

- Install Minikube

```install
minikube start --driver=docker
```

- Start Minikube

```shell
minikube start
```

- Check Running

```shell
minikube status
```

```text
minikube
type: Control Plane
host: Running
kubelet: Running
apiserver: Running
kubeconfig: Configured
```

- Validate minikube is ready

```shell
kubectl get nodes
```

```text
NAME       STATUS   ROLES           AGE    VERSION
minikube   Ready    control-plane   118m   v1.25.3
```

- Check k8s service deployment

```shell
kubectl get svc
```

```text
NAME         TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   129m
```

- services that minikube support

```shell
minikube addons list
```

```text
|-----------------------------|----------|--------------|--------------------------------|
|         ADDON NAME          | PROFILE  |    STATUS    |           MAINTAINER           |
|-----------------------------|----------|--------------|--------------------------------|
| ambassador                  | minikube | disabled     | 3rd party (Ambassador)         |
| auto-pause                  | minikube | disabled     | Google                         |
| cloud-spanner               | minikube | disabled     | Google                         |
| csi-hostpath-driver         | minikube | disabled     | Kubernetes                     |
| dashboard                   | minikube | disabled     | Kubernetes                     |
| default-storageclass        | minikube | enabled ✅   | Kubernetes                     |
| efk                         | minikube | disabled     | 3rd party (Elastic)            |
| freshpod                    | minikube | disabled     | Google                         |
| gcp-auth                    | minikube | disabled     | Google                         |
| gvisor                      | minikube | disabled     | Google                         |
| headlamp                    | minikube | disabled     | 3rd party (kinvolk.io)         |
| helm-tiller                 | minikube | disabled     | 3rd party (Helm)               |
| inaccel                     | minikube | disabled     | 3rd party (InAccel             |
|                             |          |              | [info@inaccel.com])            |
| ingress                     | minikube | disabled     | Kubernetes                     |
| ingress-dns                 | minikube | disabled     | Google                         |
| istio                       | minikube | disabled     | 3rd party (Istio)              |
| istio-provisioner           | minikube | disabled     | 3rd party (Istio)              |
| kong                        | minikube | disabled     | 3rd party (Kong HQ)            |
| kubevirt                    | minikube | disabled     | 3rd party (KubeVirt)           |
| logviewer                   | minikube | disabled     | 3rd party (unknown)            |
| metallb                     | minikube | disabled     | 3rd party (MetalLB)            |
| metrics-server              | minikube | disabled     | Kubernetes                     |
| nvidia-driver-installer     | minikube | disabled     | Google                         |
| nvidia-gpu-device-plugin    | minikube | disabled     | 3rd party (Nvidia)             |
| olm                         | minikube | disabled     | 3rd party (Operator Framework) |
| pod-security-policy         | minikube | disabled     | 3rd party (unknown)            |
| portainer                   | minikube | disabled     | 3rd party (Portainer.io)       |
| registry                    | minikube | disabled     | Google                         |
| registry-aliases            | minikube | disabled     | 3rd party (unknown)            |
| registry-creds              | minikube | disabled     | 3rd party (UPMC Enterprises)   |
| storage-provisioner         | minikube | enabled ✅   | Google                         |
| storage-provisioner-gluster | minikube | disabled     | 3rd party (Gluster)            |
| volumesnapshots             | minikube | disabled     | Kubernetes                     |
|-----------------------------|----------|--------------|--------------------------------|
```

- Access minikube services

```shell
minikube dashboard
```

- Stop minikube

```shell
minikube stop
```

- Delete minikube cluster

```shell
minikube delete
```

## Kind

    Multi-Node Cluster

- Download kind

```shell
curl.exe -Lo kind-windows-amd64.exe --ssl-no-revoke https://kind.sigs.k8s.io/dl/v0.20.0/kind-windows-amd64
Move-Item .\kind-windows-amd64 c:\k8s\kind.exe
```

- Create Cluster

```shell 
# This will create single node cluster
kind create cluster --name kind-cluster
```

- Create with configuration

```shell    
# This will create cluster with 4 nodes 2 master-nodes 2 worker-nodes
kind create cluster --name multi-node-cluster --config cluster-config1.yml
```

```shell
# Login to the multi-node cluster
kubectl config use-context multi-node-cluster
```

```shell
# Get the 4 nodes that created in the cluster 
kubectl get nodes
``` 

- Get cluster info

```shell
kubectl cluster-info --context kind-kind-cluster
```

```text
Kubernetes control plane is running at https://127.0.0.1:65458
CoreDNS is running at https://127.0.0.1:65458/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy
```

- Get clusters

```shell
kind get clusters
```

- Delete cluster

```shell
# name of the cluster without kind- prefix
kind delete cluster --name ahmed-cluster
```

## Kube Config File

    kubectl use to authenticate and login to cluster.

- Get clusters

```shell
kubectl config get-contexts
```

```text
CURRENT   NAME             CLUSTER          AUTHINFO         NAMESPACE
          docker-desktop   docker-desktop   docker-desktop
*         kind-kind        kind-kind        kind-kind
          minikube         minikube         minikube         default
```

- Switch between clusters contexts

```shell
kubectl config use-context minikube
```

```shell
# Now any command will run on cluster -  minikube 
kubectl get nodes 
kubectl get pods  
```

- get kube config file directory

```shell
ls ~/.kube/config
```

```text
Directory: C:\Users\ahmedgodaa\.kube


Mode                 LastWriteTime         Length Name
----                 -------------         ------ ----
-a----         8/21/2023   9:24 PM          11926 config

```

## Docker Registry

    Docker registry is a repository to store docker images.

### Public Docker Registry

- docker env required by minikube

```shell
    minikube docker-env
```

      $Env:DOCKER_TLS_VERIFY = "1"
      $Env:DOCKER_HOST = "tcp://127.0.0.1:54613"
      $Env:DOCKER_CERT_PATH = "C:\Users\EGYPT_LAPTOP\.minikube\certs"
      $Env:MINIKUBE_ACTIVE_DOCKERD = "minikube"
      # To point your shell to minikube's docker-daemon, run:
      # & minikube -p minikube docker-env --shell powershell | Invoke-Expression

- Set System env

```shell
$Env:DOCKER_HOST = "tcp://127.0.0.1:54613"
```

- Validate env saved

```shell
 echo $Env:DOCKER_HOST
```

- Rename exist docker image if build on local

```shell
docker tag k8s-test ahmedgodaa/k8s-test:v1.0.1
```

```shell
docker images
```

```shell
docker push ahmedgodaa/k8s-test:v1.0.1
```

- Create **Deployment**

```shell
kubectl apply -f dp-backend-dev.yml
```

- Create **Service**

```shell
kubectl apply -f k8s-test-service.yml
```

- Get **IP**

```text
 Since we expose our service as NodePort
 We can able to access it using node ip and node port.
```

```shell
kubectl get nodes -o wide
```

- Get Nodes IPs - **Minikube**

```text
NAME       STATUS   ROLES           AGE     VERSION   INTERNAL-IP    EXTERNAL-IP   OS-IMAGE             KERNEL-VERSION                      CONTAINER-RUNTIME
minikube   Ready    control-plane   3h37m   v1.25.3   192.168.49.2   <none>        Ubuntu 20.04.5 LTS   5.15.90.4-microsoft-standard-WSL2   docker://20.10.20
```

- OR

```shell
minikube ip
```

- Get Port - **Service Port**

```shell
kubectl get svc
```

```text
k8s-test     NodePort    10.108.198.207   <none>        8080:32485/TCP   8m35s
kubernetes   ClusterIP   10.96.0.1        <none>        443/TCP          3h41m
```

- Access Application - **Linux**

```text
# Access the service using node ip and node port
http://192.168.49.2:32485
```

- On **windows**

```shell
# Service should be exposed by minikube 
minikube service k8s-test
```

```text
|-----------|----------|-------------|---------------------------|
| NAMESPACE |   NAME   | TARGET PORT |            URL            |
|-----------|----------|-------------|---------------------------|
| default   | k8s-test |        8080 | http://192.168.49.2:31089 |
|-----------|----------|-------------|---------------------------|
🏃  Starting tunnel for service k8s-test.
|-----------|----------|-------------|------------------------|
| NAMESPACE |   NAME   | TARGET PORT |          URL           |
|-----------|----------|-------------|------------------------|
| default   | k8s-test |             | http://127.0.0.1:51812 |
|-----------|----------|-------------|------------------------|
🎉  Opening service default/k8s-test in default browser...
❗  Because you are using a Docker driver on windows, the terminal needs to be open to run it.
```

- Access the service - **Windows**

```text
# Access the service using tunnel
http://127.0.0.1:51812
```

### Public Docker Registry

- Start **Docker Registry Image**

```shell
docker run -d -p 5000:5000 --restart=always --name private-registry registry:2
```

- Start **Docker Registry Image Automatically**

```shell
docker run -d  -p 5000:5000 --restart=always --name registry registry:2
```

- get docker registry ip

```shell
docker inspect -f "{{ .NetworkSettings.IPAddress }}" private-registry
```

```text
172.17.0.2
```

- Tag **image**

```shell
docker tag ahmedgodaa/k8s-test:v1.0.1 localhost:5000/k8s-test:v1.0.1
```

- Push **image**

```shell
docker push localhost:5000/k8s-test:v1.0.1
```

```shell
docker pull localhost:5000/k8s-test:v1.0.1
```

- Stop **Docker Registry**

```shell
docker stop private-registry
```

- Minikube **Docker Registry**

```shell
minikube addons enable registry
```

```text
* registry is an addon maintained by Google. For any concerns contact minikube on GitHub.
You can view the list of minikube maintainers at: https://github.com/kubernetes/minikube/blob/master/OWNERS
╭──────────────────────────────────────────────────────────────────────────────────────────────────────╮
│                                                                                                      │
│    Registry addon with docker driver uses port 58201 please use that instead of default port 5000    │
│                                                                                                      │
╰──────────────────────────────────────────────────────────────────────────────────────────────────────╯
* For more information see: https://minikube.sigs.k8s.io/docs/drivers/docker
  - Using image docker.io/registry:2.8.1
  - Using image gcr.io/google_containers/kube-registry-proxy:0.4
* Verifying registry addon...
* The 'registry' addon is enabled

```

#### Access the registry - Manually

```shell
kubectl get namespaces 
```

```text
NAME                   STATUS   AGE
default                Active   18h
kube-node-lease        Active   18h
kube-public            Active   18h
kube-system            Active   18h
kubernetes-dashboard   Active   18h
```

- Get the services on the kube-system virtual cluster namespace

```shell
kubectl get svc --namespace kube-system
```

```text
NAME       TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)                  AGE
kube-dns   ClusterIP   10.96.0.10      <none>        53/UDP,53/TCP,9153/TCP   18h
registry   ClusterIP   10.106.212.23   <none>        80/TCP,443/TCP           6m55s
```

##### Expose the registry service

1. It uses ClusterIP, So we can't directly access it from outside the cluster
2. It can be port-forwarded to localhost:5000

```shell 
kubectl port-forward --namespace kube-system service/registry 5000:80
```

- **Validate** the registry is accessible

```shell
curl http://localhost:5000/v2/_catalog
```

```text
{"repositories":["k8s-test"]}
```

## ECR

```text
Repository to store docker images alternative to dockerhub, Nexus.

Amazon Elastic Container Registry (ECR) is a fully managed container registry that makes it easy to store, manage,
share, and deploy your container images and artifacts anywhere.
```

## ECS

        AWS Service to manage containers
        ECS works as minikube or Kind cluster but on the cloud.
        ECS is a container orchestration service that supports Docker containers and allows you to easily run and scale containerized applications on AWS.
        ECS eliminates the need for you to install, operate, and scale your own cluster management infrastructure.
        ECS is a regional service that simplifies running application containers in a highly available manner across multiple Availability Zones within a Region.
        ECS is a great choice to run containers for several reasons.
        First, it reduces the management overhead of running containers at scale.
        Second, it provides security capabilities, such as IAM roles for tasks, that allow you to control access to your containers.
        Finally, ECS integrates with other AWS services, such as Elastic Load Balancing, Amazon VPC, AWS IAM, and AWS CloudFormation, to provide a complete solution for running a wide range of containerized applications.

### ECS with EC2

```text
Requires Docker installed on EC2
Requires ECS Agent installed on EC2.
```

- **Pros**

```text
full access to infrastructure.
```

- **Cons**

```text
It Requires EC2 instances to run containers on.
It Requires Docker installed on EC2 instances.
Manage the Virtual Machines.
Check enough resources available on EC2 instances.
```

### ECS with Fargate

```text
Fargate is a technology that you can use with Amazon ECS to run containers without having to manage servers or clusters of Amazon EC2 instances.
Fargate is a serverless engine for containers that works with both Amazon Elastic Container Service (ECS) and Amazon Elastic Kubernetes Service (EKS).
Fargate removes the need to provision and manage servers, lets you specify and pay for resources per application.
Fargate check how uch CPU and memory your containerized application needs and it will launch the containers for you.
You don't have to select instance types or manage scaling.
```

- **Pros**

```text
Don't pay for control plane.
Integrate with AWS services.
CloudWatch for monitoring.
Elastic Load Balancing for Loadbalancing.
IAM for security.
VPC for networking.
```

## EKS

```text
EKS make easy to migrate to another cloud provider.
Amazon Elastic Kubernetes Service (Amazon EKS) is a fully managed Kubernetes service.
Kubernetes is open-source software that allows you to deploy and manage containerized applications at scale.
EKS runs Kubernetes control plane instances across multiple Availability Zones to ensure high availability.
EKS automatically detects and replaces unhealthy control plane instances, and it provides automated version upgrades and patching for them.
EKS is also integrated with many AWS services to provide scalability and security for your applications.
```

- **Pros**

```text
Full access to k8s ecosystem like helm.
```

- **Cons**

```text
Pay for the control plane unlike ECS.
Migration from EKS to another cloud provider is not easy.
```

- **How it works**

```text
Eks deploys and manages the control plane "master nodes".
Eks duplicate the control plane across multiple religious.
Eks replicate ETCD cluster across multiple religious. 
Eks deploy worker nodes on virtual EC2 instances called "compute fleet" and connect to EKS.
The worker nodes are EC2 instances that run the Kubernetes kubelet agent.
Can be semi-managed by AWS or fully managed by AWS.
```

### Eks with Fargate

```text
Make fully-managed serverless compute engine for containers.
Using Fargate with Amazon EKS allows you to run Kubernetes pods without having to provision and manage EC2 instances.
```

## Deployment

### Create Deployment

- Create deployment with 3 replicaset

```shell
kubectl create deployment test-deployment --image=ahmedgodaa/k8s-test:v1.0.1
```

- Describe deployment.

This will show the deployment details like: **Replicas** - **Selector** - **Strategy** - **Template** - **Containers**

```shell
kubectl describe deployment test-deployment
```

## Namespaces

- To specify namespace in any k8s service ( **Deployment** - **Service** - **ConfigMap** - **Secrets** )

```yaml
apiVersion: v1
kind: Deployment
metadata:
  name: k8s-test
  namespace: development
```

- To know which object accept namespace

```shell
kubectl api-resources --namespaced=true
```

    Divide cluster to virtual clusters  FE - BE - DB
    Set Permissions:
        BE only have access to DB cluster.
        Payment Cluster fully isolated.
        Device Cluster By environment. DEV - STAG - PROD
        DEV: full access 
        STG: can ssh pods troubleshoot application
        PROD: Read Access Check if application running
        PROD: Jenkins - Actions - Authorized deployment pipline

    K8s consists of multiple nodes Master - Worker
    WR nodes expose CPU and Memory resources
    Container demand the resources from WR nodes

    K8s check available resources in each node 
    K8s assign new pods based on available resources

    Each deployment can have his own namespace 
    
    Cluster have coast:
        Each cluster: at least have 6 node
            Have at least 3 master node. for high availability.
            Have at least 3 external etcd cluster.
            Have system components need to be deployed: promenades - alert-manager - ingress-controller.
            Upgrade All Clusters - Manage Certificates - Adminstration.
    Needed:
        Maximum nodes in the cluster. 
            Create new cluster. - scale horizontally.

- Create Namespace

```shell
kubectl create namespace test-namespace
```

- Get Namespaces

```shell   
kubectl get namespaces
```

- Get Pods in Namespace

```shell
kubectl get pods -n test-namespace
```

- Switch between namespaces

```shell
kubectl config set-context --current --namespace=test-namespace
```

- Validate current namespace

```shell
kubectl config view --minify
```

```yaml
apiVersion: v1
clusters:
  - cluster:
      certificate-authority: C:\Users\ahmedgodaa\.minikube\ca.crt
      extensions:
        - extension:
            last-update: Fri, 25 Aug 2023 10:01:13 EET
            provider: minikube.sigs.k8s.io
            version: v1.28.0
      namespace: test-namespace
      user: minikube
    name: minikube
current-context: minikube
kind: Config
preferences: { }
users:
  - name: minikube
    user:
      client-certificate: C:\Users\ahmedgodaa\.minikube\profiles\minikube\client.crt
      client-key: C:\Users\ahmedgodaa\.minikube\profiles\minikube\client.key
```

- OR

```shell
kubectl config get-contexts
 ```

```text
CURRENT   NAME             CLUSTER          AUTHINFO         NAMESPACE
          docker-desktop   docker-desktop   docker-desktop
          kind-kind        kind-kind        kind-kind
*         minikube         minikube         minikube         test-namespace
```

- Describe namespace

```shell
kubectl describe namespace test-namespace
```

- Return to default namespace

```shell
kubectl config set-context --current --namespace=default
```

- Delete namespace

```shell
kubectl delete namespace test-namespace
```

- Create namespace with yaml or json file

```shell
kubectl create -f namespace.yml
```

- Create development and production namespaces.

```shell
kubectl create -f https://k8s.io/examples/admin/namespace-dev.json 
kubectl create -f https://k8s.io/examples/admin/namespace-prod.json 
```

- Login into development namespace

```shell
kubectl config set-context --current --namespace=development
```

- Create deployment in development namespace

```shell
kubectl create deployment spring-test --image=ahmedgodaa/k8s-test:v1.0.1 --namespace=development   
```

```text
NAME                          READY   STATUS    RESTARTS   AGE
spring-test-c7ff98855-n797g   1/1     Running   0          11s
```

## Labels and Selectors

```text
Labels are key/value pairs that are attached to objects, such as pods.
Labels are intended to be used to specify identifying attributes of objects that are meaningful and relevant to users, but do not directly imply semantics to the core system.
Labels can be used to organize and to select subsets of objects.
Labels can be attached to objects at creation time and subsequently added and modified at any time.
Each object can have a set of key/value labels defined.
Each Key must be unique for a given object.

Selectors are the core grouping primitive in Kubernetes.
They are used to identify a set of objects that are relevant for a particular user.
A selector can be defined as a set of requirements, and any object that matches those requirements exactly will be selected.
Requirements are separated into two categories: equality-based and set-based.

equality-based requirements allow filtering by label keys and values.
set-based requirements allow filtering based upon the presence or absence of labels.
```

### Label

```yaml
# have 4 labels app - type - version - env
# should match all of them to select the pod
apiVersion: v1
kind: Pod
metadata:
  name: k8s-test
  labels:
    app: k8s-test
    type: backend
    version: v1.0.1
    env: development

```

- Selectors example

```yml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: k8s-test
  #  labels: used when i try to access the deployment from the service
  labels:
    app: k8s-test
    type: backend
    env: development
    version: v1.0.1
spec:
  selector:
    matchLabels:
      app: k8s-test
      type: backend
      version: v1.0.1
      env: development
  template:
    spec:
      containers:
        - name: k8s-test
          image: ahmedgodaa/k8s-test:v1.0.1

```

### Example

**will create 2 deployment with different versions**

- Deployment 1

```yaml
metadata:
  name: k8s-test
  labels:
    app: k8s-test
    type: backend
    version: v1.0.1
```

- Deployment 2

```yaml
metadata:
  name: k8s-test
  labels:
    app: k8s-test
    type: backend
    version: v1.0.2
```

- Create service

This service will expose only the pods with version v1.0.1
If version changed to v1.0.2 only service 2 will be exposed.

```yaml
spec:
  selector:
    matchLabels:
    app: k8s-test
    type: backend
    version: v1.0.1
```

### Deployment Selector

- Labels and selector for deployment service explained

> ### ``📝`` **Note**
>
>The .spec.selector field defines how the created ReplicaSet finds which Pods to manage. In this case, you select a
> label that is defined in the Pod template (app: nginx). However, more sophisticated selection rules are possible, as
> long as the Pod template itself satisfies the rule.

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: k8s-test
  namespace: development

  #  They provide metadata information about the deployment itself - used to accessed by other resources like services.
  #  Other services within the Kubernetes ecosystem can use these labels to identify and work with the deployment
  #  This will be associated with the deployment object

  labels:
    app: k8s-test
    env: development
    version: v1.0.1
spec:

  # The selector field defines how the Deployment finds which Pods to manage.
  # They help in selecting the pods that are controlled by this deployment.
  # Other Kubernetes components, such as services or ingress controllers, can use these labels to establish connections or routing rules to the pods managed by this deployment.


  selector:
    matchLabels:
      app: k8s-test
      env: development
      version: v1.0.1
  replicas: 2

  # The labels specified here are associated with the pods that will be created based on the template.
  # These labels can be used for various purposes, such as pod selection, service discovery, or monitoring.
  # Other services or components within the Kubernetes ecosystem can use these labels to target or interact with the pods created by this deployment.

  # Pod template  
  template:
    # By keeping the selector labels and template metadata labels the same
    # you ensure that the pods created by the deployment will have labels
    # matching the selector labels. This allows the deployment to manage and
    # control the pods effectively.
    metadata:
      labels:
        app: k8s-test
        env: development
        version: v1.0.1
    spec:
      containers:
        - name: k8s-test
          image: ahmedgodaa/k8s-test:v1.0.1
          imagePullPolicy: IfNotPresent
          ports:
            - containerPort: 8080
```

## Node Selector

nodeSelector is the simplest recommended form of node selection constraint. You can add the nodeSelector field to your
Pod specification and specify the node labels you want the target node to have. Kubernetes only schedules the Pod onto
nodes that have each of the labels you specify.

```

NODE SELECTOR IN POD AND NODE LABLES SHOULD BE THE SAME TO RUN THE POD INTO THAT NODE

```

- **Example 1**

```yaml
# Pod
apiVersion: v1
kind: Pod
metadata:
  name: k8s-test
spec:

  nodeSelector:
    app: monitoring
    version: v1.0.1

  containers:
    - name: k8s-test
      image: ahmedgodaa/k8s-test:v1.0.1
 ```

```yaml
# Node
apiVersion: v1
kind: Node
metadata:
  name: node-name
  # nodeSelector and node labels should are the same.
  labels:
    app: monitoring
    version: v1.0.1

```

**Example 2**

- Use labels to select nodes to run the application on.
    - Create kind cluster with 1 master node and 2 worker nodes
    - Worker nodes have 2 different zones.


- Create Cluster

```shell
# cluster-config2.yml have 1 master node and 2 worker nodes with different zones
kind create cluster --name test-cluster --config kind/cluster-config2.yml
```

- Get Nodes

```shell
kubectl get nodes
```

```text
NAME                               STATUS   ROLES           AGE     VERSION
multi-node-cluster-control-plane   Ready    control-plane   6m22s   v1.27.3
multi-node-cluster-worker          Ready    <none>          5m55s   v1.27.3
multi-node-cluster-worker2         Ready    <none>          5m55s   v1.27.3
```

- Get specific nodes in specific zone

```shell
 kubectl get nodes --selector zone=us-west-b
```

```text
NAME                         STATUS   ROLES    AGE   VERSION
multi-node-cluster-worker2   Ready    <none>   10m   v1.27.3
```

- Create Pod - we will not be able to know which node will run the pod

```shell
kubectl create -f pod/test-pod1.yml
```

- Specify node to run the pod using nodeSelector in the yaml
    - this will create node at all nodes with zone=us-west-b

```shell
# test-pod2.yml have nodeSelector with zone=us-west-b
kubectl create -f pod/test-pod2.yml
```

## Nodes

- Get nodes

```shell
kubectl get nodes
```

- Create Cluster

```shell
kind create cluster --name test-cluster --config kind/cluster-config2.yml
```

- Get docker containers

```shell
# Kind create the node into docker container
docker ps 
```

- Access master node container

```shell
# Master Node
docker exec -it multi-node-cluster-control-plane bash
# Worker Node
docker exec -it multi-node-cluster-worker bash
```

- See kubernetes components running on the master node

```shell
ps aux 
#OR
ps -ef | grep kube
```

- Master node k8s component

```text
1. kube-apiserver
2. kube-controller-manager
3. kube-scheduler
4. etcd
5. kubelet 
6. kube-proxy
```

- Worker node k8s component

```text
1. kubelet
2. kube-proxy
3. container runtime - containerd - docker
```

### Kubelet

```text
Agent running on all nodes report status of nodes and pods to master node
```

- Check kubelet status - after login into the node

```shell
systemctl status kubelet
```

- make problem into kubelet by move its configuration file

```text
mkdir /temp/
mv /etc/kubernetes/kubelet.conf /temp/
```

- Restart kubelet process

```shell
systemctl restart kubelet
```

- Check the node - Node status will be NotReady

```shell
kubectl get nodes
 ```

```text
# Node status will be NotReady because api-server not able to access to kubelet.
# Scheduler will not be able to assign pods on this node again.

NAME                               STATUS     ROLES           AGE   VERSION
multi-node-cluster-control-plane   Ready      control-plane   19h   v1.27.3
multi-node-cluster-worker          NotReady   <none>          19h   v1.27.3
multi-node-cluster-worker2         Ready      <none>          19h   v1.27.3
```

- Solve the issue

```shell
mv /temp/kubelet.conf /etc/kubernetes/kubelet.conf
systemctl restart kubelet
systemctl status kubelet
```

```text
NAME                               STATUS     ROLES           AGE   VERSION
multi-node-cluster-control-plane   Ready      control-plane   19h   v1.27.3
multi-node-cluster-worker          Ready      <none>          19h   v1.27.3
multi-node-cluster-worker2         Ready      <none>          19h   v1.27.3
```

- Get pods running on a node

```shell
kubectl get pods  -o wide --field-selector spec.nodeName=multi-node-cluster-worker
```

### Node Maintenance

- Cordoning a node

```shell
# Cordoning a node will prevent scheduler from assigning new pods to this node
kubectl cordon multi-node-cluster-worker
```

- Drain OR Migrate all pods from a node

```shell
# Migrate all pods from a node to another node
kubectl drain multi-node-cluster-worker --ignore-daemonsets
```

- After maintenance - uncordon the node

```shell
kubectl uncordon multi-node-cluster-worker
```

## Resources

```text
Resource Quota - Quality of Service -  Limit Range
```

## Pods

```text
Pod should not be a code runs for one time it will lead to CrashLoopBackOf or restart.
Pod should be running process like web server or database.
```

- Check Pod running on which node.

```shell
kubectl get pods -o wide
```

- Create Multi-Container Pod

```shell
kubectl create -f pod/test-pod3.yml
```

- Get logs from specific container

```shell
 kubectl logs two-containers-pod -c k8s-test
```

- Access container shell

```shell
kubectl exec -it two-containers-pod -c k8s-test sh
```

- Make file inside the container

```shell
kubectl exec -it two-containers-pod -c k8s-test sh
touch /tmp/test.txt
```

- Copy file inside the container

```shell 
# not working
kubectl cp E:\k8s\k8s\README.md two-containers-pod:/temp/README.md
```

### Readiness Probe

```text
Readiness probes are used to know when a pod is ready to serve traffic.
Readiness probes are defined in the pod spec.
```

- Example

Spring Boot Actuator provides a number of additional features to help you monitor and manage your application when it’s
pushed to production.

Use the /actuator/health endpoint to get basic application health information.

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: k8s-test
spec:
  containers:
    - name: k8s-test
    image: ahmedgodaa/k8s-test:v1.0.1
    imagePullPolicy: IfNotPresent
    ports:
      - containerPort: 8080
    readinessProbe:
      httpGet:
      path: /actuator/health
      port: 8080
      httpHeaders:
        - name: Custom-Header
          value: to-ke-en-ex-am-pl-e
      initialDelaySeconds: 5
      periodSeconds: 5
  ```

### Liveness Probe

**liveness probe not implemented yet**
[Reference](https://spring.io/blog/2020/03/25/liveness-and-readiness-probes-with-spring-boot)

```text
Liveness probes are used to know when a pod is alive or dead.
Liveness probes are defined in the pod spec.
Once the liveness probe fails a pod will be restarted.
```

## Multi-Container Pods

```text
Multi-Container Pods are pods that contain more than one container.
Multi-Container Pods are used in cases where multiple processes need to work together.
```

- Create Multi-Container Pod

```shell
# both containers have shared mount volume
kubectl create -f pod/test-pod4.yml
```

- Access the pod shell

```shell
kubectl exec -it sidecar-container-demo sh
```

- Use bash

```shell
bash
```

- Access Created Html file which created by debian container in the nginx container.

```shell
# this html file will be deployed on the nginx container
cd /usr/share/nginx/html
cat index.html
```

## Resource Quota

```text
ResourceQuota is a policy resource that specifies 
compute resource requirements (such as memory and CPU) 
and object count limits 
(such as maximum count of pods per namespace) for namespaces.
```

### Quality of Service

```text
Quality of Service (QoS) is a concept in Kubernetes that allows the cluster to make intelligent scheduling decisions.
QoS is three classes:
    Guaranteed 
    Burstable
    BestEffort
Guaranteed: 
    The pod is guaranteed to have the requested resources available at all times.
    If the node fails, the pod is guaranteed to be restarted on a node with the requested resources.
    Guaranteed pods are the highest priority in the system.
    If the cluster runs out of resources, the Guaranteed pods are the last to be evicted.
```

- Guaranteed

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: guaranteed-pod
  namespace: default
spec:
  containers:

  ```

```

- Burstable

```text
Burstable: 
    The pod is not guaranteed to have requested resources available at all times.
    If the node fails, the pod may not be restarted on a node with the requested resources.
    Burstable pods are the second highest priority in the system.
    If the cluster runs out of resources, the Burstable pods are the second to be evicted.
```

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: burstable-pod
  namespace: default
spec:
  containers:
#    - name: a
#        image: nginx
#        resources:
#            requests:
#            cpu: 100m
#            memory: 100Mi
#            limits:
#            cpu: 200m
#            memory: 200Mi
```

- BestEffort

```text
BestEffort: 
    The pod is not guaranteed to have requested resources available at all times.
    If the node fails, the pod may not be restarted on a node with the requested resources.
    BestEffort pods are the lowest priority in the system.
    If the cluster runs out of resources, the BestEffort pods are the first to be evicted.
```

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: besteffort-pod
  namespace: default
```

### Network Policy

```text
how a pod is allowed to communicate with various network "entities" (other pods, Service endpoints, external IPs, etc).
```

## Helm

```text
    Helm is a tool for managing Kubernetes charts. Charts are packages of pre-configured Kubernetes resources.
    Helm is a tool that streamlines installing and managing Kubernetes applications.
```

- Get all helm releases

```shell
helm list
```

- Install Mysql using helm

```shell
helm install my-sql oci://registry-1.docker.io/bitnamicharts/mysql
```

- Validate mysql

```shell
kubectl  kubectl get pods -w --namespace default
```

```text
> Note:

Execute the following to get the administrator credentials:

echo Username: root
MYSQL_ROOT_PASSWORD=$(kubectl get secret --namespace default my-sql-mysql -o jsonpath="{.data.mysql-root-password}" | base64 -d)

To connect to your database:

1. Run a pod that you can use as a client:

   kubectl run my-sql-mysql-client --rm --tty -i --restart='Never' --image  docker.io/bitnami/mysql:8.0.34-debian-11-r31 --namespace default --env MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASSWORD --command -- bash

2. To connect to primary service (read/write):

   mysql -h my-sql-mysql.default.svc.cluster.local -uroot -p"$MYSQL_ROOT_PASSWORD"

```

- Get the mysql release secret as json

```shell
kubectl get secret --namespace default my-sql-mysql -o json
```

```json
{
  "apiVersion": "v1",
  "data": {
    "mysql-password": "MWFVRkk3UTFPdw==",
    "mysql-root-password": "V0VIb3l4Vjc5WQ=="
  },
  "kind": "Secret",
  "metadata": {
    "annotations": {
      "meta.helm.sh/release-name": "my-sql",
      "meta.helm.sh/release-namespace": "default"
    },
    "creationTimestamp": "2023-08-31T15:15:09Z",
    "labels": {
      "app.kubernetes.io/instance": "my-sql",
      "app.kubernetes.io/managed-by": "Helm",
      "app.kubernetes.io/name": "mysql",
      "helm.sh/chart": "mysql-9.12.1"
    },
    "name": "my-sql-mysql",
    "namespace": "default",
    "resourceVersion": "109476",
    "uid": "cba38e54-c79f-40d4-b61e-ead7afc27bd0"
  },
  "type": "Opaque"
}
```

- Get the mysql release secret as yaml

```shell
kubectl get secret --namespace default my-sql-mysql -o yaml
```

```yaml
apiVersion: v1
data:
  mysql-password: MWFVRkk3UTFPdw==
  mysql-root-password: V0VIb3l4Vjc5WQ==
kind: Secret
metadata:
  annotations:
    meta.helm.sh/release-name: my-sql
    meta.helm.sh/release-namespace: default
  creationTimestamp: "2023-08-31T15:15:09Z"
  labels:
    app.kubernetes.io/instance: my-sql
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: mysql
    helm.sh/chart: mysql-9.12.1
  name: my-sql-mysql
  namespace: default
  resourceVersion: "109476"
  uid: cba38e54-c79f-40d4-b61e-ead7afc27bd0
type: Opaque
```

- Get the mysql password from the path direct

```shell
kubectl get secret --namespace default my-sql-mysql -o jsonpath="{.data.mysql-root-password}"
```

- Install Prometheus using helm

1. Create kind cluster because it didn't work on minikube

```shell
kind creawte cluster --name helm-test-cluster
# change the context to the new cluster
kubectl config use-context helm-test-cluster
```

2. Install prometheus using helm

```shell
2. Install prometheus using helm


```shell
# Add prometheus helm repo
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
# Validate repo added 
helm repo  list
# Update helm repo
helm repo update
#Install Helm Chart
helm install prometheus prometheus-community/kube-prometheus-stack
```

- Get created pods of helm prometheus installation

```shell
kubectl --namespace default get pods -l "release=prometheus"
#OR to see all created pods 
kubectl get pods -o wide 
# Node the grafana pods
```

- Validate the prometheus service so to access prometheus

```shell
kubectl get services
```

- Will access the prometheus using the grafana service

```shell
kubectl port-forward --namespace default service/prometheus-grafana 3000:80
```

- Access prometheus using the browser

```shell
http://localhost:3000
```

- Temp way of changing the service type

> Edit the type from **ClusterIP** to **NodePort**

```shell
kubectl edit service prometheus-grafana 
```

- Access the chart configuration

> `📝` This can be applied to any helm chart

```shell
helm show values prometheus-community/kube-prometheus-stack
```

- Direct the output to a file

```shell
helm show values prometheus-community/kube-prometheus-stack > prometheus-values.yml
```

- We are able to find the password on the yml for grafana

```yaml
  adminPassword: prom-operator
```

- OR we can use this command with linux to get the password

```shell
helm show values prometheus-community/kube-prometheus-stack | grep adminPassword
```

- Override the values

```shell
helm upgrade prometheus prometheus-community/kube-prometheus-stack --values helm-chart-values/prometheus-values.yml
```

- Override the values - admin password using cmd

```shell
helm upgrade prometheus prometheus-community/kube-prometheus-stack --set grafana.adminPassword=test.Password123
```

> Login with new password to grafana.
> Make Sure to port-forward the service after again apply for new services and pods.

- Change the Cluster Service Type

> Check Documentation [Search Github](https://github.com/grafana/helm-charts/tree/main/charts/grafana)

```shell
kubectl upgrade prometheus prometheus-community/kube-prometheus-stack --set grafana.service.type=NodePort
```

- OR - using the yml

```yaml
  service:
    portName: http-web
    type: NodePort
```

- Use override separate value file

```shell
helm upgrade prometheus prometheus-community/kube-prometheus-stack --values helm-chart-values/prometheus-override-values-test1.yml
```

### Avoid SnowFlack Server

> #### **Note📝**
> If we want to create a new cluster in different server of the current server we need to return back to the server
> history and see what changes we have done on this cluster on the past and this is impossible so once we need to create
> new cluster we need to have script all the configurations steps are defined so once we want to create the new cluster
> we run the script and this will replicate the cluster.

- Uninstall current chart

```shell
helm uninstall prometheus
```

- Pull remote helm chart

```shell
# It will be "helm pull + github.url "
# OR 
# "Helm pull + added repo artifactory"
# This downloaded tar file 
helm pull prometheus-community/kube-prometheus-stack
# This will untar the file 
helm pull prometheus-community/kube-prometheus-stack --untar 
```

- Helm install from local chart

```shell
helm install prometheus ./helm-charts/kube-prometheus-stack
```

- Validate the chart installed

```shell
helm list
kubectl get pods 
kubectl get services  
```

> `📝` Note:
> Don't change in the values file instead make override file that override the values of the chart
> so whenever you want to upgrade the pulled chart you don't need to edit one by one the updated value before.
> like file  `helm-chart-values/prometheus-override-values-test1.yml`