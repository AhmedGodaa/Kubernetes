# Documentation

- --

## Download K8s

- --

1. Download **Api-Server**

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/amd64/kube-apiserver"
chmod +x kube-apiserver
```

2. Download **Controller-Manager**

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/amd64/kube-controller-manager"
chmod +x kube-controller-manager
```

3. Download **Scheduler**

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/amd64/kube-scheduler"
chmod +x kube-scheduler
```

4. Download **Kube-Proxy**

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/amd64/kube-proxy"
chmod +x kube-proxy
```

5. Download **Kubelet**

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/amd64/kubelet"
chmod +x kubelet
```

6. Download **Kubectl**

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/amd64/kubectl"
chmod +x kubectl
```

7. Download **etcd**

```shell
curl -LO "https://github.com/etcd-io/etcd/releases/download/v3.5.9/etcd-v3.5.9-linux-amd64.tar.gz"
tar -xvf etcd-v3.5.9-linux-arm64.tar.gz
rm -r etcd-v3.5.9-linux-arm64.tar.gz
```

* **After download all files should be executable**

![image](https://github.com/AhmedGodaa/k8s/assets/73083104/88867d6d-16e8-460d-82a2-09e15aef3dc5)

## Running K8s

- --

### Running etcd

- --



1.Run  **etcd**

```shell
./etcd-v3.5.9-linux-amd64/etcd etcd-v3.5.9-linux-amd64
```

2. Validate **etcd**

```shell
./etcd-v3.5.9-linux-amd64/etcdctl member list -w table
```

- **Output** - etcd running local on port 2379

```text
+------------------+---------+---------+-----------------------+-----------------------+------------+
|        ID        | STATUS  |  NAME   |      PEER ADDRS       |     CLIENT ADDRS      | IS LEARNER |
+------------------+---------+---------+-----------------------+-----------------------+------------+
| 8e9e05c52164694d | started | default | http://localhost:2380 | http://localhost:2379 |      false |
+------------------+---------+---------+-----------------------+-----------------------+------------+
```

3. check tables headlth

```shell
./etcd-v3.5.9-linux-amd64/etcdctl endpoint status -w table
```

- **output**

```text
+----------------+------------------+---------+---------+-----------+------------+-----------+------------+--------------------+--------+
|    ENDPOINT    |        ID        | VERSION | DB SIZE | IS LEADER | IS LEARNER | RAFT TERM | RAFT INDEX | RAFT APPLIED INDEX | ERRORS |
+----------------+------------------+---------+---------+-----------+------------+-----------+------------+--------------------+--------+
| 127.0.0.1:2379 | 8e9e05c52164694d |   3.5.9 |   20 kB |      true |      false |         2 |          4 |                  4 |        |
+----------------+------------------+---------+---------+-----------+------------+-----------+------------+--------------------+--------+
```

### Running kube-apiserver

- --

1. Run kube-apiserver

```shell
./kube-apiserver --etcd-servers=http://localhost:2379
```

2. validate stored values after running apiserver

```shell
./etcd-v3.5.9-linux-amd64/etcdctl get / --prefix --keys-only
```

3. get stored namespaces

```shell
./etcd-v3.5.9-linux-amd64/etcdctl get / --prefix --keys-only | grep namespaces
```

4. get stored deployments

```shell
./etcd-v3.5.9-linux-amd64/etcdctl get / --prefix --keys-only | grep deployments
```

### Use kubectl "_frontend_"

- --

1. get pods

```shell
./kubectl get po
```

2. get namespaces

```shell
./kubectl get namespaces
```

3. create deployments

```shell
./kubectl create deployment --image=spring spring-1
```

4. get replicaset

```shell
./kubectl get rs
```

5. get deployment

```shell
./kubectl get deployments
```

- **output**


     - deployment not ready 
     - image not pulled and deployment 
     - controller manager is down

```text

```

### Running Controller-Manager

- --

    controller manager responsible to create replcaset for deployments

1. run controller-manager

```shell
./kube-controller-manager -master=http://localhost:8080
```

2. validate replicaset creation

```shell
./kubectl get rs 
```
3. descripte recast to see why diserd is 1 and current is 0 

- output

```text

```


### Running Controller-Manager

- --