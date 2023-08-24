curl.exe -Lo kind-windows-amd64.exe --ssl-no-revoke https://kind.sigs.k8s.io/dl/v0.20.0/kind-windows-amd64
Move-Item .\kind-windows-amd64 c:\k8s\kind.exe

# This will create single node cluster
./kind create cluster --name kind-cluster


# This will create cluster with 4 nodes 2 master-nodes 2 worker-nodes
./kind create cluster --name multi-node-cluster --config kind-config.yml

# Login to the multi-node cluster
kubectl config use-context multi-node-cluster

# Get the 4 nodes that created in the cluster
kubectl get nodes

kubectl cluster-info --context kind-kind-cluster

./kind get clusters

./kind get clusters