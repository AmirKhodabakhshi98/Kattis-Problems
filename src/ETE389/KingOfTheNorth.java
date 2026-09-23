package ETE389;
import Kattis.Kattio;


public class KingOfTheNorth {

    private static final long intMax = Integer.MAX_VALUE;


    //inits cells to all possible neighbours + inside edge
    private static void initEdgesAllDirections(MaxFlow.Node[][] orig, MaxFlow.Node[][] fake){
        for (int r = 0; r < orig.length; r++) {
            for (int c = 0; c < orig[0].length; c++) {
                edgeInit(orig[r][c], fake[r][c], orig[r][c].vertexCapacity);//INNRE

                if (orig[r][c].vertexCapacity==0 || orig[r][c].isCastle){
                    continue;
                }

                if (c+1<orig[0].length) {
                    edgeInit(fake[r][c], orig[r][c+1], intMax);
                }
                if (c-1>=0){
                    edgeInit(fake[r][c], orig[r][c-1], intMax);
                }
                if (r-1>=0){
                    edgeInit(fake[r][c], orig[r-1][c], intMax);
                }
                if (r+1<orig.length){
                    edgeInit(fake[r][c], orig[r+1][c], intMax);
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

    //initializes pseudo source node to all grid edge cells
    private static void initSource(MaxFlow.Node[][] orig, MaxFlow.Node source){
        for (int r = 0; r < orig.length; r++) {
            if (orig[r][0].vertexCapacity!=0){
                edgeInit(source,orig[r][0], intMax);//v
            }
            if (orig[r][orig[0].length-1].vertexCapacity!=0){
                edgeInit(source,orig[r][orig[0].length-1], intMax);//h
            }
        }
        for(int c = 1; c < orig[0].length-1; c++){
            if (orig[0][c].vertexCapacity!=0){
                edgeInit(source,orig[0][c], intMax);//topp

            }
            if (orig[orig.length-1][c].vertexCapacity!=0){
                edgeInit(source,orig[orig.length-1][c], intMax);//ner
            }
        }
    }

    public static void main(String[] args) {
            Kattio io = new Kattio();
            int rows = io.getInt();
            int cols = io.getInt();

            MaxFlow.Node[] nodes =  new MaxFlow.Node[rows*cols*2+1];
            MaxFlow.Node[][] nodes2d = new MaxFlow.Node[rows][cols];
            MaxFlow.Node[][] nodesFake2d = new MaxFlow.Node[rows][cols];
            nodes[nodes.length-1] = new MaxFlow.Node();

            int i = 0;
            for (int r = 0; r < rows; r++){
                for (int c = 0; c < cols; c++){
                    MaxFlow.Node n = new MaxFlow.Node();
                    MaxFlow.Node nFake = new MaxFlow.Node(true);
                    nodes2d[r][c] = n;
                    nodesFake2d[r][c] = nFake;
                    n.vertexCapacity = io.getInt();
                    n.ID=i;
                    nFake.ID=i+1;
                    nodes[i++] = n;
                    nodes[i++] = nFake;
                }
            }

            int castleRow = io.getInt();
            int castleCol = io.getInt();
            nodes2d[castleRow][castleCol].isCastle = true;

            initEdgesAllDirections(nodes2d, nodesFake2d);
            initSource(nodes2d,nodes[nodes.length-1]);
            MaxFlow mf = new MaxFlow(nodes.length-1, nodesFake2d[castleRow][castleCol].ID,nodes);

            io.println(mf.maxFlow);
            io.flush();
            io.close();
    }
}
