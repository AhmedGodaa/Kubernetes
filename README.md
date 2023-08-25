# Documentation

## Download K8s

legacy version

- Download **Api-Server**

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/amd64/kube-apiserver"
chmod +x kube-apiserver
```

- Download **Controller-Manager**

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/amd64/kube-controller-manager"
chmod +x kube-controller-manager
```

- Download **Scheduler**

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/amd64/kube-scheduler"
chmod +x kube-scheduler
```

- Download **Kube-Proxy**

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/amd64/kube-proxy"
chmod +x kube-proxy
```

- Download **Kubelet**

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/amd64/kubelet"
chmod +x kubelet
```

- Download **Kubectl**

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/amd64/kubectl"
chmod +x kubectl
```

- Download **etcd**

```shell
curl -LO "https://github.com/etcd-io/etcd/releases/download/v3.5.9/etcd-v3.5.9-linux-amd64.tar.gz"
tar -xvf etcd-v3.5.9-linux-arm64.tar.gz
rm -r etcd-v3.5.9-linux-arm64.tar.gz
```

## Running K8s

### Running etcd

- Run  **etcd**

```shell
./etcd-v3.5.9-linux-amd64/etcd 
```

- Validate **etcd**

```shell
./etcd-v3.5.9-linux-amd64/etcdctl member list -w table
```

```text
+------------------+---------+---------+-----------------------+-----------------------+------------+
|        ID        | STATUS  |  NAME   |      PEER ADDRS       |     CLIENT ADDRS      | IS LEARNER |
+------------------+---------+---------+-----------------------+-----------------------+------------+
| 8e9e05c52164694d | started | default | http://localhost:2380 | http://localhost:2379 |      false |
+------------------+---------+---------+-----------------------+-----------------------+------------+
```

- check tables health

```shell
./etcd-v3.5.9-linux-amd64/etcdctl endpoint status -w table
```

```text
+----------------+------------------+---------+---------+-----------+------------+-----------+------------+--------------------+--------+
|    ENDPOINT    |        ID        | VERSION | DB SIZE | IS LEADER | IS LEARNER | RAFT TERM | RAFT INDEX | RAFT APPLIED INDEX | ERRORS |
+----------------+------------------+---------+---------+-----------+------------+-----------+------------+--------------------+--------+
| 127.0.0.1:2379 | 8e9e05c52164694d |   3.5.9 |   20 kB |      true |      false |         2 |          4 |                  4 |        |
+----------------+------------------+---------+---------+-----------+------------+-----------+------------+--------------------+--------+
```

### Running kube-apiserver

- Run kube-apiserver

```shell
./kube-apiserver --etcd-servers=http://localhost:2379
```

- validate stored values after running apiserver

```shell
./etcd-v3.5.9-linux-amd64/etcdctl get / --prefix --keys-only
```

- get stored namespaces

```shell
./etcd-v3.5.9-linux-amd64/etcdctl get / --prefix --keys-only | grep namespaces
```

- get stored deployments

```shell
./etcd-v3.5.9-linux-amd64/etcdctl get / --prefix --keys-only | grep deployments
```

### Use kubectl "_frontend_"

1. get pods

```shell
./kubectl get po
```

- get namespaces

```shell
./kubectl get namespaces
```

- create deployments

```shell
./kubectl create deployment --image=spring spring-1
```

- get replicaset

```shell
./kubectl get rs
```

- Get deployment

```shell
./kubectl get deployments
```

- **output**.

- --

     - deployment not ready 
     - image not pulled and deployment 
     - controller manager is down

```text
 
```

### Running Controller-Manager

- --

    controller manager responsible to create replcaset for deployments

- Run controller-manager

```shell
./kube-controller-manager -master=http://localhost:8080
```

- Validate replicaset creation

```shell
./kubectl get rs 
```

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
./kind create cluster --name kind-cluster
```

- Create with configuration

```shell    
# This will create cluster with 4 nodes 2 master-nodes 2 worker-nodes
./kind create cluster --name multi-node-cluster --config kind-config.yml
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
./kind get clusters
```

- Delete cluster

```shell
./kind delete cluster-name
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
kubectl apply -f k8s-test-deploy.yml
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

- **Prose**

```text
Full access to k8s ecosystem like helm.
```

- **Cons**

```text
Pay for the control plane unlike ECS.
Migration from EKS to another cloud provider is not easy.
```

## Namespaces

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
