package edu.dtcc.spaol1.phrasedatabasemanager;

public class Phrase
{
    // Variables
    int _id;
    String _phrase_title;
    String _phrase;

    // Empty constructor
    public Phrase() {}

    // All parameter constructor
    public Phrase(int _id, String _phrase, String _phrase_title)
    {
        this._id = _id;
        this._phrase = _phrase;
        this._phrase_title = _phrase_title;
    }

    // Three parameter constructor
    public Phrase(String _phrase_title, String _phrase)
    {
        this._phrase_title = _phrase_title;
        this._phrase = _phrase;
    }

    // Getters
    public int get_id()
    {
        return _id;
    }

    public String get_phrase()
    {
        return _phrase;
    }

    public String get_phrase_title()
    {
        return _phrase_title;
    }

    // Setters
    public void set_id(int _id)
    {
        this._id = _id;
    }

    public void set_phrase_title(String _phrase_title)
    {
        this._phrase_title = _phrase_title;
    }

    public void set_phrase(String _phrase)
    {
        this._phrase = _phrase;
    }
}