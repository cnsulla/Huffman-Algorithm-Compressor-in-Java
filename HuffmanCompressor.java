public class HuffmanCompressor
{
    HeapToHuff heap;
    SerializeTree en;
    DeserializeTree de;
    HuffmanNode huffTreeTurned;
    CompressImage comp;
    DecompressImage decomp;

    String imgIn = "512x288";
    String treeOut = "tree2.fl";
    String imgOut = "compressed.xl";

    public HuffmanCompressor()
    {
        heap = new HeapToHuff(imgIn);
        HuffmanNode huffTreeOrig = heap.getHuffmanTree();
        
        en = new SerializeTree(huffTreeOrig);
        System.out.println("Tree Serialized \n-------------");

        de = new DeserializeTree(treeOut);
        huffTreeTurned = de.returnTree();
        System.out.println("Tree Deserialized \n-----------");
        // de.printInorder(huffTreeOrig);
        // de.printInorder(huffTreeTurned);

        System.out.println("Image file writing...\n-----------");
        comp = new CompressImage(huffTreeOrig, huffTreeOrig.freq, imgIn, imgOut);
        System.out.println("Finished... \n-----------");

        decomp = new DecompressImage(huffTreeOrig, treeOut, imgOut);
    }   

    public static void main(String[] args)
    {
        new HuffmanCompressor();
    }
}