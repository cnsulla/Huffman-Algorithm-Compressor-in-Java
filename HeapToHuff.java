public class HeapToHuff
{
    private ImageScan imgScan = new ImageScan();
    private HuffmanNode[] list;
    private PriorityQueue pq;

    public HeapToHuff(String FILENAME)
    {
        System.out.println("received path: "+FILENAME);
        getPArray(FILENAME);
    }

    public void getPArray(String FILEPATH)
    {
        list = imgScan.ImageScan(FILEPATH);
        pq = new PriorityQueue(list.length);

        int testerX = 0;
        for (int i = 0; i < list.length; i++)
        {
            testerX+=list[i].freq;
            pq.insert( list[i] );
        }
        pq.minHeap();

        System.out.println("Number of unique pixels: "+pq.getSize());
        System.out.println(testerX);
    }

    public HuffmanNode getHuffmanTree()
    {
        // System.out.println("Called Tree Conversion with initial size: "+pq.getSize());
        while (pq.getSize() > 1)
        {
            HuffmanNode leftNode = pq.dequeue();
            HuffmanNode rightNode = pq.dequeue();

            HuffmanNode temp = new HuffmanNode(0, (leftNode.freq + rightNode.freq));
            temp.setLeft(leftNode);
            temp.setRight(rightNode);
            
            pq.insert(temp);
        }
        pq.minHeap();

        System.out.println("Head of HuffmanTree has frequency: " + pq.peek().freq);
        System.out.println("L: " + pq.peek().left.freq + " R: " + pq.peek().right.freq);
        return pq.peek();
    }
}