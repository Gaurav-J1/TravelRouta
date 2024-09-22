#include<bits/stdc++.h>
#include<unistd.h>
using namespace std;
#define CHARACTER_SIZE 256
class TrieNode
{
    TrieNode *children[CHARACTER_SIZE];
    bool isEndOfWord;
    public:
    TrieNode *getNode()
    {
        TrieNode *pNode =  new TrieNode;
        pNode->isEndOfWord = false; 
        for (int i=0;i<CHARACTER_SIZE;i++)
            pNode->children[i] = NULL;
        return pNode;
    }
    void insert(TrieNode *root,string username,string password)
    {
        TrieNode *temp;
        int index=int(username[0]);
        if(root->children[index]==NULL)
        {
            temp=getNode();
            root->children[index] = temp;
        }
        if(username.size()==1)
        {
            root->isEndOfWord= true;
            if(!password.empty())
            {
                insert(root->children[index],password,"");
            }
            return;
        }
        insert(root->children[index],username.substr(1),password);
    }
    bool search(TrieNode *root,string username,string password) 
    {
        int index=int(username[0]);
        if(root->children[index]==NULL)
        {
            return false;
        }
        if(username.size()>1)
        {
            return search(root->children[index],username.substr(1),password);
        }
        if(root->isEndOfWord && !password.empty())
        {
            return search(root->children[index],password,"");
        }
        else
        {
            return root->isEndOfWord;
        }
    }
};
class registration
{
    public:
    string first_name;
    string last_name;
    string mobile_no;
    string email_id;
    string username;
    string password;
    void input()
    {
        cout<<"\n\t\tEnter your first name:- ";
        cin>>first_name;
        cout<<"\n\t\tEnter your last name:- ";
        cin>>last_name;
        cout<<"\n\t\tEnter your mobile no:- ";
        cin>>mobile_no;
        cout<<"\n\t\tEnter your email id:- ";
        cin>>email_id;
        cout<<"\n\t\tSet username:- ";
        cin>>username;
        cout<<"\n\t\tSet password:- ";
        cin>>password;
    }
    void display()
    {
        cout<<"\n\t\tName:- "<<first_name+" "+last_name;
        cout<<"\n\t\tMobile no:- "<<mobile_no;
        cout<<"\n\t\tEmail id:- "<<email_id;
        cout<<"\n\t\tUsername:- "<<username;
        cout<<"\n\t\tPassword:- "<<password;
    }
};
class Node
{
    registration r;
    Node* next;
    public:
    void push(Node** head_ref,registration r1)
    {
        Node* new_node=new Node();
        new_node->r=r1;
        new_node->next=(*head_ref);
        (*head_ref)=new_node;
    }
};
vector<string> str{"DELHI","MUMBAI","KOLKATA","AGRA","BHOPAL","BANGLORE","CHENNAI","CHANDIGARH"};
vector<pair<int,vector<pair<int,int>>>> graph[9];
void addEdge(int frm,int to,vector<pair<int,int>> weight)
{
    graph[frm].push_back({to,weight});
    graph[to].push_back({frm,weight});
}
void printPath(int parent[], int j)
{
    if(parent[j]==-1)
        return;
    printPath(parent,parent[j]);
    cout<<"->"<<str[j-1];
}
void print_distance(int d[],int S,int D,int V,int path[],int choice,int cost[])
{
    if(choice==1)
    {
        cout<<"\n\t\t\tTravel time:- "<<d[D];
        cout<<"\n\t\t\tCost of travelling:- "<<cost[D];
    }
    else if(choice==2)
    {
        cout<<"\n\t\t\tTravel time:- "<<cost[D];
        cout<<"\n\t\t\tCost of travelling:- "<<d[D];
    }
    cout<<"\n\t\t\tPath:- "<<str[S-1];
    printPath(path,D);
    /*while(S!=D)
    {
        cout<<S<<" ";
        cout<<str[S-1]<<"->";
        S=path[S];
    }
    cout<<str[S-1];*/
}
void shortestPathFaster(int S,int V,int choice,int D)
{
	int d[V+1];
    int cost[V+1];
	bool inQueue[V+1]={false};
    int path[V+1];
	for (int i=0;i<=V;i++) 
    {
		d[i]=INT_MAX;
	}
	d[S]=0;
    cost[S]=0;
    path[S]=-1;
	queue<int> q;
	q.push(S);
	inQueue[S]=true;
	while (!q.empty())
    {
		int u=q.front();
		q.pop();
		inQueue[u]=false;
		for (int i=0;i<graph[u].size();i++) 
        {
			int v=graph[u][i].first;
			int weight=INT_MAX;
            for(int j=0;j<2;j++)
            {
                if(choice==1 && graph[u][i].second[j].first!=-1)
                {
                    weight=min(weight,graph[u][i].second[j].first);
                }
                else if(choice==2 && graph[u][i].second[j].second!=-1)
                {
                    weight=min(weight,graph[u][i].second[j].second);
                }
            }
			if(d[v]>d[u]+weight) 
            {
				d[v]=d[u]+weight;
                if(choice==1)
                {
                    cost[v]=cost[u]+max(graph[u][i].second[0].second,graph[u][i].second[1].second);
                }
                else if(choice==2)
                {
                    cost[v]=cost[u]+max(graph[u][i].second[0].first,graph[u][i].second[1].first);
                }
                path[v]=u;
				if(!inQueue[v]) 
                {
					q.push(v);
					inQueue[v]=true;
				}
			}
		}
	}
    print_distance(d,S,D,V,path,choice,cost);
}
int main()
{
    int ch;
    TrieNode t;
    TrieNode *root=t.getNode();
    addEdge(1,8,{{55,5500},{286,570}});
    addEdge(1,4,{{-1,-1},{252,300}});
    addEdge(1,2,{{135,8500},{1560,2800}});
    addEdge(1,3,{{125,7700},{1020,2500}});
    addEdge(2,6,{{95,7500},{1003,2200}});
    addEdge(6,7,{{80,4700},{383,350}});
    addEdge(4,5,{{-1,-1},{360,400}});
    addEdge(8,3,{{125,1200},{1295,3000}});
    addEdge(3,7,{{130,9000},{1560,3800}});
    string password,username;
    system("CLS");
    system("Color E4");
    cout<<"\n\n\n\n\n\n\n\n\n\t\t\t\t\t---------------";
    cout<<"\n\t\t\t\t\t|BEELINE TRAVEL|";
    cout<<"\n\t\t\t\t\t---------------";
    sleep(1);
    while(1)
    {
        system("CLS");
        cout<<"\n\n\n\n\n\t\t\t\t\t***MENU***";
        cout<<"\n\t\t\t1.LOGIN";
        cout<<"\n\t\t\t2.REGISTER";
        cout<<"\n\t\t\t3.EXIT";
        int choice;
        cout<<"\n\t\t\tEnter your choice:- ";
        cin>>choice;
        if(choice==1)
        {
            int check=0;
            while(check<3)
            {
                int flag=1;
                system("CLS");
                cout<<"\n\n\n\n\n\t\t\t\t\t**LOGIN**";
                cout<<"\n\t\t\tEnter username:- ";
                cin>>username;
                cout<<"\n\t\t\tEnter password:- ";
                cin>>password;
                if(!t.search(root,username,password))
                {
                    check++;
                    cout<<"\n\t\t\t**WRONG USERNAME OR PASSWORD**";
                    sleep(1);
                    if(check>=3)
                    {
                        system("CLS");
                        cout<<"\n\n\n\n\n\t\t\t\t**LOGIN LIMIT EXCEED**";
                        sleep(1);
                        exit(0);
                    }
                }
                else
                {
                    check=0;
                    while(1)
                    {
                        system("CLS");
                        cout<<"\n\n\n\n\n\t\t\t\t\t**MENU**";
                        cout<<"\n\t\t\t1.PROFILE";
                        cout<<"\n\t\t\t2.TRAVEL";
                        cout<<"\n\t\t\t3.LOGOUT";
                        int choice1;
                        cout<<"\n\t\t\tEnter your choice:- ";
                        cin>>choice1;
                        system("CLS");
                        if(choice1==1)
                        {

                        }
                        else if(choice1==2)
                        {
                            cout<<"\n\t\t\n\t\t\t\t\t**CITY CHOICE**";
                            cout<<"\n\t\t\t1.DELHI";
                            cout<<"\n\t\t\t2.MUMBAI";
                            cout<<"\n\t\t\t3.KOLKATA";
                            cout<<"\n\t\t\t4.AGRA";
                            cout<<"\n\t\t\t5.BHOPAL";
                            cout<<"\n\t\t\t6.BANGLORE";
                            cout<<"\n\t\t\t7.CHENNAI";
                            cout<<"\n\t\t\t8.CHANDIGARH";
                            int source,destination;
                            cout<<"\n\t\t\tEnter your source:- ";
                            cin>>source;
                            cout<<"\n\t\t\tEnter your destination:- ";
                            cin>>destination;
                            cout<<"\n\t\t\n\t\t\t\t\t**MENU**";
                            cout<<"\n\t\t\t1.SHORTEST PATH TRAVEL";//by aeroplane
                            cout<<"\n\t\t\t2.LOWEST COST TRAVEL";//by road
                            int choice2;
                            cout<<"\n\t\t\tEnter your choice:- ";
                            cin>>choice2;
                            shortestPathFaster(source,8,choice2,destination);
                            sleep(10);
                        }
                        else if(choice1==3)
                        {
                            system("CLS");
                            cout<<"\n\n\n\n\n\n\t\t\t\t\t\t\t***THANK YOU FOR LOGIN*";
                            sleep(1);
                            flag=0;
                            break;
                        }
                        else
                        {
                            cout<<"\n\t\t\t\t\t\tWRONG CHOICE ENTERED";
                        }
                    }
                    if(flag==0)
                    {
                        break;
                    }
                }
            }
        }
        else if(choice==2)
        {
            system("CLS");
            cout<<"\n\n\n\n\n\t\t\t\t\t\t\t**REGISTRATION**";
            registration r;
            r.input();
            t.insert(root,r.username,r.password);
        }
        else if(choice==3)
        {
            exit(0);
        }
        else
        {
            cout<<"\n\t\t\t\t\t\tWRONG CHOICE ENTERED";
        }
    }
    return 0;
}
