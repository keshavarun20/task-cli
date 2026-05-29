public class Task{
  String title;
  boolean completed;

  @Override
  public String toString() {
    return title + " | " + completed;
  }
}
