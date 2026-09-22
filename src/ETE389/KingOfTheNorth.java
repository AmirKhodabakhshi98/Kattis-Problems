package ETE389;
import Kattis.Kattio;


public class KingOfTheNorth {

    private static final long longMax = Long.MAX_VALUE;//overflow??

/*
    private static void initSource(MaxFlow.Node nodes, MaxFlow.Node[][] nodes2d, MaxFlow.Node source){
        for (int i = 0; i < nodes2d.length; i++){
            edgeInit(nodes);
        }

    }

    //row col kolla om fel
    private static void initEdgesAllDirections(MaxFlow.Node[] nodes,int rows, int cols) {
        int upIndex = 0;
        int downIndex = 0;
        int leftIndex = -3;
        int rightIndex = 1;

        for(int i = 0; i < nodes.length; i++){//+=2){
            //MaxFlow.Node node = nodes[i];
            if (nodes[i].isCastle){
                continue;
            }
            //if (node.vertexCapacity !=0 && !node.isCastle){ återinför sen om för långsmt

            //    edgeInit(nodes,i,i,node.vertexCapacity);
                //inre edge
            if (!nodes[i].isFakeNode){
                edgeInit(nodes,i,i+1,nodes[i].vertexCapacity);
                continue;
                }

            if (i+upIndex>=0){
                edgeInit(nodes,i,i+upIndex,intMax);
            }
            if (i+downIndex<nodes.length){
                edgeInit(nodes,i,i+downIndex,intMax);
            }
            if (i+leftIndex>=0){
                edgeInit(nodes,i,i+leftIndex,intMax);
            }
            if (i+rightIndex<nodes.length){
                edgeInit(nodes,i,i+rightIndex,intMax);
            }


        }
    }
*/

    private static void initEdgesAllDirections(MaxFlow.Node[][] orig, MaxFlow.Node[][] fake){


        for (int r = 0; r < orig.length; r++) {
            for (int c = 0; c < orig[0].length; c++) {
                if (orig[r][c].isCastle || orig[r][c].vertexCapacity==0){
                    continue;
                }

                edgeInit(orig[r][c], fake[r][c], orig[r][c].vertexCapacity);//INNRE

                if (c+1<orig[0].length) {
                    edgeInit(fake[r][c], orig[r][c+1], longMax);
                }
                if (c-1>=0){
                    edgeInit(fake[r][c], orig[r][c-1], longMax);
                }
                if (r-1>=0){
                    edgeInit(fake[r][c], orig[r-1][c], longMax);
                }
                if (r+1<orig.length){
                    edgeInit(fake[r][c], orig[r+1][c], longMax);
                }
            }
        }





    }

    private static void edgeInit(MaxFlow.Node from,MaxFlow.Node to, long capacity){
        MaxFlow.Edge edge = new MaxFlow.Edge(from, to, capacity,true);
        MaxFlow.Edge reverseEdge = new MaxFlow.Edge(to, from, 0, false);
        edge.reverseEdge = reverseEdge;
        reverseEdge.reverseEdge = edge;
        from.edges.add(edge);
        to.edges.add(reverseEdge);
    }

    private static void initSource(MaxFlow.Node[][] orig, MaxFlow.Node source){
        for (int r = 0; r < orig.length; r++) {
            if (orig[r][0].vertexCapacity!=0){
                edgeInit(source,orig[r][0], longMax);//v
            }
            if (orig[r][orig[0].length-1].vertexCapacity!=0){
                edgeInit(source,orig[r][orig[0].length-1], longMax);//h
            }
        }
        for(int c = 0; c < orig[0].length; c++){
            if (orig[0][c].vertexCapacity!=0){
                edgeInit(source,orig[0][c], longMax);//topp

            }
            if (orig[orig.length-1][c].vertexCapacity!=0){
                edgeInit(source,orig[orig.length-1][c], longMax);//ner
            }
        }
    }

    public static void main(String[] args) {
            Kattio io = new Kattio();
            int rows = io.getInt(); //felsök här om nåt dumt händer med grid direction
            int cols = io.getInt();
            MaxFlow.Node[] nodes =  new MaxFlow.Node[rows*cols*2+1];
            MaxFlow.Node[][] nodes2d = new MaxFlow.Node[rows][cols];
            MaxFlow.Node[][] nodesFake2d = new MaxFlow.Node[rows][cols];
            nodes[nodes.length-1] = new MaxFlow.Node();

            //förenkla allt deta dumma loopandetom för långsmat
            int i = 0;
            for (int r = 0; r < rows; r++){
                for (int c = 0; c < cols; c++){
                    MaxFlow.Node n = new MaxFlow.Node();
                    MaxFlow.Node nFake = new MaxFlow.Node(true);
                    nodes2d[r][c] = n;
                    nodesFake2d[r][c] = nFake;
                    n.vertexCapacity = io.getInt();
                    if (n.vertexCapacity>0){
                        n.vertexCapacity = 100000;
                    }
                    //nFake.vertexCapacity = n.vertexCapacity;
                    n.ID=i;
                    nFake.ID=i+1;
                    nodes[i++] = n;
                    nodes[i++] = nFake;
                    //tempNodes[r][c] = n;
                }
            }
            int castleRow = io.getInt();
            int castleCol = io.getInt();
           // nodes[(castleRow*cols+castleCol)*2].isCastle = true;
            //nodes[(castleRow*cols+castleCol)*2+1].isCastle = true;
            nodes2d[castleRow][castleCol].isCastle = true;
            //nodes2d[castleRow][castleCol].isCastle = true;

            initEdgesAllDirections(nodes2d, nodesFake2d);
            initSource(nodes2d,nodes[nodes.length-1]);
            //MaxFlow mf = new MaxFlow(nodes.length-1, castleCol*castleRow*2,nodes);

            MaxFlow mf = new MaxFlow(nodes.length-1, nodes2d[castleRow][castleCol].ID,nodes);
   //     System.err.println(mf.maxFlow);

        io.println(mf.maxFlow);
        io.flush();
        io.close();
    }



}
