# Documentation

## Download K8s - Legacy

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

* **After download all files should be executable**

![image](https://github.com/AhmedGodaa/k8s/assets/73083104/88867d6d-16e8-460d-82a2-09e15aef3dc5)

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

- Describe recast to see why expected is 1 and current is 0.

```text

```

### Running Controller-Manager

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

- Create multi-node cluster

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
./kind delete cluster -n cluster-name
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
