package pe.org.incn.support;

/**
 * CollectionUtils
 *
 * @author enea <enea.so@live.com>
 */
public class CollectionUtils {

  public static String[] join(String [] ... parms) {
      
    int size = 0;
    for (String[] array : parms) {
      size += array.length;
    }

    String[] result = new String[size];

    int j = 0;
    for (String[] array : parms) {
      for (String s : array) {
        result[j++] = s;
      }
    }
    return result;
  }
}
