package com.example.prostykod;

public class NotepadService {

    private static NotepadService notepadService;

    public static NotepadService getInstance() {
        if (notepadService == null) {
            notepadService = new NotepadService();
        }
        return notepadService;
    }

    private String content;

    private NotepadService() {
        this.content = "Empty";
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
