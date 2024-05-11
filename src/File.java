import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class File {
    String fname;

    public File() {
    }

    public File(String fname) {
        this.fname = fname;
    }

    /**
     * 获取
     * @return fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * 设置
     * @param fname
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    public String GetFileData() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(this.fname);
        int ch;
        StringBuilder sb = new StringBuilder();
        while ((ch = fileInputStream.read())!=-1){
            char currentchar = (char)ch;
            if(currentchar == '\r'){
                char nextchar = (char)fileInputStream.read();
                if(nextchar == '\n'){
                    sb.append(" ");

                }
            } else if (!Character.isLetterOrDigit(currentchar)&&!Character.isWhitespace(currentchar)) {
                sb.append(" ");
            }
            else {
                sb.append(currentchar);
            }

        }
        fileInputStream.close();
        return sb.toString().replaceAll("\\s+", " ");

    }
    //获取用于构建图的节点元素
    public ArrayList<String> GetNodedata() throws IOException {
        ArrayList<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.GetFileData().length(); i++) {
            if(Character.isWhitespace(this.GetFileData().charAt(i))){
                result.add(sb.toString());
                sb = new StringBuilder();
            }else{
                sb.append(this.GetFileData().charAt(i));
            }
        }
        return result;
    }
}
