public class PriorityQueue
{
    private MinHeap heap;
    private int heapSize = 0;

    public PriorityQueue(int length)
    {
        heap = new MinHeap(length);
    }

    public void insert(HuffmanNode input)
    {
        heap.insert(input);
        
        heapSize++;
    }

    public HuffmanNode dequeue()
    {
        HuffmanNode ret = heap.remove();
        heapSize--;
        return ret;
    }

    public void minHeap()
    {
        heap.minHeap();
    }

    public HuffmanNode peek()
    {
        return heap.getTop();
    }

    public int getSize()
    {
        return heapSize;
    }

    public void print()
    {
        heap.print();
    }
}
