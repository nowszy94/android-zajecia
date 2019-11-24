package com.example.prostykod;

public class NotepadService {

    private static NotepadService notepadService;

    public static NotepadService getInstance() {
        if (notepadService == null) {
            notepadService = new NotepadService();
        }
        return notepadService;
    }

    private byte[] data;

    private NotepadService() {
        this.data = new byte[0];
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
