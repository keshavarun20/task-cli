public class Task{
  private String title;
  private boolean completed;

  public String getTitle() {
    return this.title;
  }
  public boolean isCompleted() {
    return this.completed;
  }

  public void setTitle(String title) {
    if (title == null || title.isEmpty()) return;
    this.title = title;
  }
  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  @Override
  public String toString() {
    return title + " | " + completed;
  }
}
