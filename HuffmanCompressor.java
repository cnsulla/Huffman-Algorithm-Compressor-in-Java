public class HuffmanCompressor
{
    HeapToHuff heap;
    SerializeTree en;
    DeserializeTree de;
    HuffmanNode huffTreeTurned;
    CompressImage comp;

    public HuffmanCompressor()
    {
        heap = new HeapToHuff("test4");
        HuffmanNode huffTreeOrig = heap.getHuffmanTree();
        
        
        en = new SerializeTree(huffTreeOrig);
        System.out.println("Tree Serialized \n-------------");

        de = new DeserializeTree("tree2.fl");
        huffTreeTurned = de.returnTree();
        System.out.println("Tree Deserialized \n-----------");
        // de.printInorder(huffTreeOrig);
        // de.printInorder(huffTreeTurned);

        System.out.println("Image file writing.. \n-----------");
        comp = new CompressImage(huffTreeOrig, huffTreeOrig.freq, "test4", "compressed.XLV");
        System.out.println("Finished.. \n-----------");
    }   

    public static void main(String[] args)
    {
        new HuffmanCompressor();
    }
}