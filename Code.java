import java.util.*;
import java.util.Queue;

class TrieNode {
    private static final int CHARACTER_SIZE = 256;
    private TrieNode[] children;
    private boolean isEndOfWord;

    public TrieNode() {
        this.children = new TrieNode[CHARACTER_SIZE];
        this.isEndOfWord = false;
    }

    public TrieNode getNode() {
        TrieNode pNode = new TrieNode();
        pNode.isEndOfWord = false;
        for (int i = 0; i < CHARACTER_SIZE; i++) {
            pNode.children[i] = null;
        }
        return pNode;
    }

    public void insert(TrieNode root, String username, String password) {
        TrieNode temp;
        int index = (int) username.charAt(0);
        if (root.children[index] == null) {
            temp = getNode();
            root.children[index] = temp;
        }
        if (username.length() == 1) {
            root.isEndOfWord = true;
            if (!password.isEmpty()) {
                insert(root.children[index], password, "");
            }
            return;
        }
        insert(root.children[index], username.substring(1), password);
    }

    public boolean search(TrieNode root, String username, String password) {
        int index = (int) username.charAt(0);
        if (root.children[index] == null) {
            return false;
        }
        if (username.length() > 1) {
            return search(root.children[index], username.substring(1), password);
        }
        if (root.isEndOfWord && !password.isEmpty()) {
            return search(root.children[index], password, "");
        } else {
            return root.isEndOfWord;
        }
    }
}

class Registration {
    String firstName;
    String lastName;
    String mobileNo;
    String emailId;
    String username;
    String password;

    public void input() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n\t\tEnter your first name:- ");
        firstName = scanner.nextLine();
        System.out.print("\n\t\tEnter your last name:- ");
        lastName = scanner.nextLine();
        System.out.print("\n\t\tEnter your mobile no:- ");
        mobileNo = scanner.nextLine();
        System.out.print("\n\t\tEnter your email id:- ");
        emailId = scanner.nextLine();
        System.out.print("\n\t\tSet username:- ");
        username = scanner.nextLine();
        System.out.print("\n\t\tSet password:- ");
        password = scanner.nextLine();
    }

    public void display() {
        System.out.println("\n\t\tName:- " + firstName + " " + lastName);
        System.out.println("\n\t\tMobile no:- " + mobileNo);
        System.out.println("\n\t\tEmail id:- " + emailId);
        System.out.println("\n\t\tUsername:- " + username);
        System.out.println("\n\t\tPassword:- " + password);
    }
}

class Node {
    Registration r;
    Node next;

    public void push(Node head_ref, Registration r1) {
        Node new_node = new Node();
        new_node.r = r1;
        new_node.next = head_ref;
        head_ref = new_node;
    }
}

public class Main {
    static List<String> str = Arrays.asList("DELHI", "MUMBAI", "KOLKATA", "AGRA", "BHOPAL", "BANGLORE", "CHENNAI", "CHANDIGARH");
    static List<List<Pair<Integer, List<Pair<Integer, Integer>>>>> graph = new ArrayList<>(9);

    public static void addEdge(int frm, int to, List<Pair<Integer, Integer>> weight) {
        graph.get(frm).add(new Pair<>(to, weight));
        graph.get(to).add(new Pair<>(frm, weight));
    }

    public static void printPath(int[] parent, int j) {
        if (parent[j] == -1)
            return;
        printPath(parent, parent[j]);
        System.out.print("->" + str.get(j - 1));
    }

    public static void printDistance(int[] d, int S, int D, int V, int[] path, int choice, int[] cost) {
        if (choice == 1) {
            System.out.println("\n\t\t\tTravel time:- " + d[D]);
            System.out.println("\n\t\t\tCost of traveling:- " + cost[D]);
        } else if (choice == 2) {
            System.out.println("\n\t\t\tTravel time:- " + cost[D]);
            System.out.println("\n\t\t\tCost of traveling:- " + d[D]);
        }
        System.out.print("\n\t\t\tPath:- " + str.get(S - 1));
        printPath(path, D);
    }

    public static void shortestPathFaster(int S, int V, int choice, int D) {
        int[] d = new int[V + 1];
        int[] cost = new int[V + 1];
        boolean[] inQueue = new boolean[V + 1];
        int[] path = new int[V + 1];

        for (int i = 0; i <= V; i++) {
            d[i] = Integer.MAX_VALUE;
        }
        d[S] = 0;
        cost[S] = 0;
        path[S] = -1;
        Queue<Integer> q = new LinkedList<>();
        q.add(S);
        inQueue[S] = true;

        while (!q.isEmpty()) {
            int u = q.poll();
            inQueue[u] = false;

            for (int i = 0; i < graph.get(u).size(); i++) {
                int v = graph.get(u).get(i).getKey();
                int weight = Integer.MAX_VALUE;
                for (int j = 0; j < 2; j++) {
                    if (choice == 1 && graph.get(u).get(i).getValue().get(j).getKey() != -1) {
                        weight = Math.min(weight, graph.get(u).get(i).getValue().get(j).getKey());
                    } else if (choice == 2 && graph.get(u).get(i).getValue().get(j).getValue() != -1) {
                        weight = Math.min(weight, graph.get(u).get(i).getValue().get(j).getValue());
                    }
                }

                if (d[v] > d[u] + weight) {
                    d[v] = d[u] + weight;
                    if (choice == 1) {
                        cost[v] = cost[u] + Math.max(graph.get(u).get(i).getValue().get(0).getValue(), graph.get(u).get(i).getValue().get(1).getValue());
                    } else if (choice == 2) {
                        cost[v] = cost[u] + Math.max(graph.get(u).get(i).getValue().get(0).getKey(), graph.get(u).get(i).getValue().get(1).getKey());
                    }
                    path[v] = u;
                    if (!inQueue[v]) {
                        q.add(v);
                        inQueue[v] = true;
                    }
                }
            }
        }
        printDistance(d, S, D, V, path, choice, cost);
    }

    public static void main(String[] args) {
        int ch;
        TrieNode t = new TrieNode();
        TrieNode root = t.getNode();
        addEdge(1, 8, Arrays.asList(new Pair<>(55, 5500), new Pair<>(286, 570)));
        addEdge(1, 4, Arrays.asList(new Pair<>(-1, -1), new Pair<>(252, 300)));
        addEdge(1, 2, Arrays.asList(new Pair<>(135, 8500), new Pair<>(1560, 2800)));
        addEdge(1, 3, Arrays.asList(new Pair<>(125, 7700), new Pair<>(1020, 2500)));
        addEdge(2, 6, Arrays.asList(new Pair<>(95, 7500), new Pair<>(1003, 2200)));
        addEdge(6, 7, Arrays.asList(new Pair<>(80, 4700), new Pair<>(383, 350)));
        addEdge(4, 5, Arrays.asList(new Pair<>(-1, -1), new Pair<>(360, 400)));
        addEdge(8, 3, Arrays.asList(new Pair<>(125, 1200), new Pair<>(1295, 3000)));
        addEdge(3, 7, Arrays.asList(new Pair<>(130, 9000), new Pair<>(1560, 3800));
        String password, username;

        System.out.print("\n\n\n\n\n\n\n\n\n\t\t\t\t\t---------------");
        System.out.print("\n\t\t\t\t\t|BEELINE TRAVEL|");
        System.out.print("\n\t\t\t\t\t---------------");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\n\n\n\n\n\t\t\t\t\t***MENU***");
            System.out.print("\n\t\t\t1.LOGIN");
            System.out.print("\n\t\t\t2.REGISTER");
            System.out.print("\n\t\t\t3.EXIT");
            int choice;
            System.out.print("\n\t\t\tEnter your choice:- ");
            choice = scanner.nextInt();

            if (choice == 1) {
                int check = 0;
                while (check < 3) {
                    int flag = 1;
                    System.out.print("\n\n\n\n\n\t\t\t\t\t**LOGIN**");
                    System.out.print("\n\t\t\tEnter username:- ");
                    username = scanner.next();
                    System.out.print("\n\t\t\tEnter password:- ");
                    password = scanner.next();

                    if (!t.search(root, username, password)) {
                        check++;
                        System.out.print("\n\t\t\t**WRONG USERNAME OR PASSWORD**");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (check >= 3) {
                            System.out.print("\n\n\n\n\n\t\t\t\t**LOGIN LIMIT EXCEED**");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.exit(0);
                        }
                    } else {
                        check = 0;
                        while (true) {
                            System.out.print("\n\n\n\n\n\t\t\t\t\t**MENU**");
                            System.out.print("\n\t\t\t1.PROFILE");
                            System.out.print("\n\t\t\t2.TRAVEL");
                            System.out.print("\n\t\t\t3.LOGOUT");
                            int choice1;
                            System.out.print("\n\t\t\tEnter your choice:- ");
                            choice1 = scanner.nextInt();

                            if (choice1 == 1) {

                            } else if (choice1 == 2) {
                                System.out.print("\n\t\t\n\t\t\t\t\t**CITY CHOICE**");
                                System.out.print("\n\t\t\t1.DELHI");
                                System.out.print("\n\t\t\t2.MUMBAI");
                                System.out.print("\n\t\t\t3.KOLKATA");
                                System.out.print("\n\t\t\t4.AGRA");
                                System.out.print("\n\t\t\t5.BHOPAL");
                                System.out.print("\n\t\t\t6.BANGLORE");
                                System.out.print("\n\t\t\t7.CHENNAI");
                                System.out.print("\n\t\t\t8.CHANDIGARH");
                                int source, destination;
                                System.out.print("\n\t\t\tEnter your source:- ");
                                source = scanner.nextInt();
                                System.out.print("\n\t\t\tEnter your destination:- ");
                                destination = scanner.nextInt();
                                System.out.print("\n\t\t\n\t\t\t\t\t**MENU**");
                                System.out.print("\n\t\t\t1.SHORTEST PATH TRAVEL");//by aeroplane
                                System.out.print("\n\t\t\t2.LOWEST COST TRAVEL");//by road
                                int choice2;
                                System.out.print("\n\t\t\tEnter your choice:- ");
                                choice2 = scanner.nextInt();
                                shortestPathFaster(source, 8, choice2, destination);
                                try {
                                    Thread.sleep(10000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } else if (choice1 == 3) {
                                System.out.print("\n\n\n\n\n\n\t\t\t\t**THANK YOU FOR LOGIN*");
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                flag = 0;
                                break;
                            } else {
                                System.out.print("\n\t\t\t\t\t\tWRONG CHOICE ENTERED");
                            }
                        }
                        if (flag == 0) {
                            break;
                        }
                    }
                }
            } else if (choice == 2) {
                System.out.print("\n\n\n\n\n\t\t\t\t\t\t\t**REGISTRATION**");
                Registration r = new Registration();
                r.input();
                t.insert(root, r.username, r.password);
            } else if (choice == 3) {
                System.exit(0);
            } else {
                System.out.print("\n\t\t\t\t\t\tWRONG CHOICE ENTERED");
            }
        }
    }
}
