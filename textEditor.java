package textEditor;
import java.util.*;
public class textEditor{
    Node head= new Node(null);
    Node tail= new Node(null);
   
    Node cursor= new Node('|');
    String filename;
    int countsaves=0;
    HashMap<String,Node> savedStatus= new HashMap<>();
    
    public void editor(){
        Create("untitled");
    }
    public void Create(String filename){
        this.filename=filename;
        this.head.next=cursor;
        this.cursor.prev=head;
        this.tail.prev=cursor;
        this.cursor.next=tail;

    }
    public void addText(String text){
        char[] tx = Objects.requireNonNull(text).toCharArray();
        for(char c: tx){
            Node ch= new Node(c,this.cursor.prev,this.cursor);
            this.cursor.prev.next=ch;
            this.cursor.prev=ch;

        }
    }
    public void deleteText(int k){
        int del=0;
        Node curr=this.cursor.prev;
        while(del<k && curr.prev!=null){
            curr=curr.prev;
            del++;
        }
        curr.next=this.cursor;
        this.cursor.prev=curr;
        
    }
    public String show(){
        StringBuilder sb= new StringBuilder();
        Node n = head.next;
        while (n != tail) {
            if (n != cursor) {
                sb.append(n.value);
            }
            n = n.next;
        }
        return sb.toString();
    }
    public void moveCursorLeft(int k){
        int l=0;
        while(l<k && cursor.prev!=head){
            Node left=this.cursor.prev; // detaching cursor from its left
            left.next=this.cursor.next;
            this.cursor.next.prev=left;

            left.prev.next=this.cursor; // inserting cursor before it
            this.cursor.prev=left.prev;
            this.cursor.next=left;
            left.prev=this.cursor;
           l++;
            
        }

    }
    public void moveCursorRight(int k){
        int r=0;
        while(r<k && cursor.next!=tail){
            Node right= this.cursor.next; // detaching cursor from its right
            right.prev=this.cursor.prev;
            this.cursor.prev.next=right;

            right.next.prev=this.cursor;
            this.cursor.next=right.next; // inserting after it
            this.cursor.prev=right;
            right.next=this.cursor;
            r++;

            
        }
    }
    public String save(){
        Node Copytexts=copy(this.head);
        countsaves++;
        savedStatus.put(filename+"-("+countsaves+")", Copytexts);
        return filename+"-("+countsaves+")";
    }
    public Node copy(Node head){
        Node newhead=new Node(head.value);
        Node dummy=head.next;
        Node newcurr=newhead;

        while(dummy.next!=null){
            newcurr.next= new Node(dummy.next.value);
            newcurr.next.prev=newcurr;
            newcurr=newcurr.next;
            dummy=dummy.next;
        }
        return newhead;


    }

    
    
    

    public static void main (String[] args) {
        textEditor editor = new textEditor();  
        editor.Create("sample");          
        
        
        editor.addText("Hello, world!");
        System.out.println("After addText:   " + editor.show());
        
        
        editor.addText(" Welcome to Java.");
        System.out.println("After more text: " + editor.show());
        System.out.println("Saved:"+ editor.save());
        
        editor.moveCursorLeft(17);

        editor.moveCursorRight(11);
        System.out.println("Saved:"+ editor.save());

        
        editor.deleteText(6);
        System.out.println("After deleteText: " + editor.show());
        
        
        editor.deleteText(100);
        System.out.println("After clearing:   " + editor.show());
        System.out.println("Saved:"+ editor.save());
    }



    class Node{
        Character value;
        Node next;
        Node prev;
        Node (Character value){
            this(value,null,null);
        }
        Node(Character value, Node prev, Node next){
            this.value=value;
            this.prev =prev;
            this.next=next;

        }
    }
    
}
