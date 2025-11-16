/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author ERR0R
 */
public class Lesson {
    int lessonID;
    String title;
    String content;
    private List<String> resources = new ArrayList<>();
    public Lesson(int lessonID, String title, String content) {
        this.lessonID = lessonID;
        this.title = title;
        this.content = content;
    }
    public void setLessonID(int lessonID){this.lessonID=lessonID;}
    public void setTitle(String title){this.title=title;}
    public void setContent(String content){this.content=content;}
    public void setResources(List<String> resources) { this.resources = resources; }
    public List<String> getResources() { return resources; }
    public int getLessonID(){return lessonID;}
    public String getTitle(){return title;}
    public String getContent(){return content;}
    public void addResource(String resource) { resources.add(resource); }
    public void removeResource(String resource) { resources.remove(resource); }

    @Override
    public String toString() {
    return "Lesson{" +
           "lessonID=" + lessonID +
           ", title='" + title + '\'' +
           ", content='" + content +
           '}';
    }

}
