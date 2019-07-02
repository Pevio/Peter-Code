import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;

//A word class to store needed information for a word from the ontology
class Word {
    String word;
    String meaning;
    
    Word(String word) {
        this(word, "");
    }
    Word(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }
    @Override
    public String toString() {
        return word;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Word)) return false;
        Word w = (Word)o;
        return this.word.equals(w.word);
    }
}

/* Provides a user interface companion for a Phase 1 assistent in the TBTA software.
 * Finds problems, words, meanings, and suggestions for what can be done in Phase 1.
*/

public class TBTAA extends Application {
    //Info from database
    ArrayList<String> tables = new ArrayList<String>();
    ArrayList<Word>[] wordLists;
    int verbList = -1;
    ArrayList<String[]> irregularWords = new ArrayList<String[]>();
    ArrayList<String> hyphenated = new ArrayList<String>();
    ArrayList<String> dehyphenated = new ArrayList<String>();
    
    ArrayList<String> foundWords = new ArrayList<String>();
    ArrayList<ArrayList<String>> foundWordMeanings = new ArrayList<ArrayList<String>>();
    
    //GUI
    Button whyFound = new Button("Why was this word found?");
    Button whyNotFound = new Button("Why was this word not found?");
    Button whatAreSuggestions = new Button("What are suggestions?");
    final String TEXT_START = "";
    TextArea text = new TextArea(TEXT_START);
    Button test = new Button("Analyze");
    final String WORDS_START = "Words:\n";
    Label words = new Label(WORDS_START);
    final String PROBLEMS_START = "Problems:\n";
    Label problems = new Label(PROBLEMS_START);
    final String SUGGESTIONS_START = "Suggestions:\n";
    Label suggestions = new Label(SUGGESTIONS_START);
    final String MEANINGS_START = ""; boolean meaningsLocked = false;
    Label meanings = new Label(MEANINGS_START);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Sets up the screen and loads all words
        
        //GUI
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 700, 550);
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(whyFound, whyNotFound, whatAreSuggestions);
        hbox.relocate(10, 10);
        setDim(text, 680, 120);             text.relocate(10, 50);
        setDim(test, 100, 50);              test.relocate(420, 10);
        setWidth(meanings, 240);
        
        problems.relocate(10, 180);
        words.relocate(200, 180);
        suggestions.relocate(360, 180);
        meanings.relocate(450, 180);
        meanings.setWrapText(true);
        text.setWrapText(true);
        pane.getChildren().addAll(hbox, text, words, problems, suggestions, meanings);
        
        //Handlers
        whyFound.setOnAction(e -> clickWhyFound());
        whyNotFound.setOnAction(e -> clickWhyNotFound());
        whatAreSuggestions.setOnAction(e -> clickWhatAreSuggestions());
        text.setOnKeyReleased(e -> analyze());
        test.setOnAction(e -> analyze());
        text.requestFocus();
        text.setFont(new Font(14));
        words.setOnMouseMoved(e -> mouseWords(e.getY()));
        words.setOnMouseClicked(e -> clickWords());
        words.setOnMouseExited(e -> mouseExitWords());
        
        stage.setTitle("Phase 1 Assistant");
        stage.setScene(scene);
        stage.show();

        //SQL
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
    
        //Initialize database connection
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        }
        catch(ClassNotFoundException cnfex) {}
        try {
            String msAccDB = "Ontology.mdb";
            String dbURL = "jdbc:ucanaccess://" + msAccDB; 

            connection = DriverManager.getConnection(dbURL); 
            statement = connection.createStatement();
        }
        catch(SQLException sqlex){}
        
        //Get table names
        DatabaseMetaData md = connection.getMetaData();
        ResultSet rs = md.getTables(null, null, "%", null);
        while (rs.next()) {
            tables.add(rs.getString(3));
        }
        wordLists = new ArrayList[tables.size()];

        //Get words
        for (int i = 0; i < tables.size(); i++) {
            wordLists[i] = new ArrayList<Word>();
            String table = tables.get(i);
            ArrayList<Word> list = new ArrayList<Word>();

            //Try to get words from the table
            try {
                resultSet = statement.executeQuery("SELECT * FROM " + table);

                // processing returned data
                while(resultSet.next()) {
                    String word = resultSet.getString(2);
                    String meaning = "";
                    try {
                        meaning = resultSet.getString(8);
                    } catch (Exception e) {}

                    list.add(new Word(word, meaning));
                    if (word.contains("-") && !word.equals("-1")) {
                        hyphenated.add(word);
                        dehyphenated.add(word.replace("-", " "));
                        hyphenated.add(word);
                        dehyphenated.add(word.replace("-", ""));
                    }
                }
            }
            catch(SQLException sqlex){}

            if (table.equals("Pronouns")) {
                //Manually add pronouns
                list.add(new Word("he"));
                list.add(new Word("she"));
                list.add(new Word("him"));
                list.add(new Word("her"));
                list.add(new Word("his"));
                list.add(new Word("you"));
                list.add(new Word("your"));
                list.add(new Word("I"));
                list.add(new Word("me"));
                list.add(new Word("my"));
                list.add(new Word("they"));
                list.add(new Word("them"));
                list.add(new Word("himself"));
                list.add(new Word("we"));
                list.add(new Word("us"));
                list.add(new Word("our"));
                list.add(new Word("it"));
            } else if (table.equals("Particles")) {
                list.add(new Word("the"));
                list.add(new Word("a"));
                list.add(new Word("an"));
                list.add(new Word("that"));
                list.add(new Word("those"));
            } else if (table.equals("Adjectives")) {
                list.add(new Word("what"));
            } else if (table.equals("Adverbs")) {
                list.add(new Word("who"));
                list.add(new Word("whom"));
                list.add(new Word("when"));
                list.add(new Word("where"));
                list.add(new Word("why"));
                list.add(new Word("how"));
            }
            if (tables.get(i).equals("Verbs")) verbList = i;
            
            wordLists[i] = list;
        }
        // Close database connection
        try {
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (SQLException sqlex) {}
        
        setupIrregulars();
    }
    public final void setupIrregulars() {
        //The list of forms of irregular verbs
        irregularWords.add(new String[]{"be", "was", "were", "been", "is", "am", "are"});
        irregularWords.add(new String[]{"bear", "bore", "borne"});
        irregularWords.add(new String[]{"beat", "beaten"});
        irregularWords.add(new String[]{"begin", "began", "begun"});
        irregularWords.add(new String[]{"bite", "bit", "bitten"});
        irregularWords.add(new String[]{"bleed", "bled"});
        irregularWords.add(new String[]{"blow", "blew", "blown"});
        irregularWords.add(new String[]{"break", "broke", "broken"});
        irregularWords.add(new String[]{"bring", "brought"});
        irregularWords.add(new String[]{"build", "built"});
        irregularWords.add(new String[]{"burn", "burnt", "burned"});
        irregularWords.add(new String[]{"buy", "bought"});
        irregularWords.add(new String[]{"catch", "caught"});
        irregularWords.add(new String[]{"choose", "chose", "chosen"});
        irregularWords.add(new String[]{"come", "came"});
        irregularWords.add(new String[]{"dig", "dug"});
        irregularWords.add(new String[]{"do", "did", "done"});
        irregularWords.add(new String[]{"dream", "dreamed", "dreamt"});
        irregularWords.add(new String[]{"drink", "drank", "drunk"});
        irregularWords.add(new String[]{"dwell", "dwelt", "dwelled"});
        irregularWords.add(new String[]{"eat", "ate", "eaten"});
        irregularWords.add(new String[]{"fall", "fell", "fallen"});
        irregularWords.add(new String[]{"feed", "fed"});
        irregularWords.add(new String[]{"feel", "felt"});
        irregularWords.add(new String[]{"fight", "fought"});
        irregularWords.add(new String[]{"find", "found"});
        irregularWords.add(new String[]{"fly", "flew", "flown"});
        irregularWords.add(new String[]{"forbid", "forbade", "forbidden"});
        irregularWords.add(new String[]{"forget", "forgot", "forgotten"});
        irregularWords.add(new String[]{"get", "got", "gotten"});
        irregularWords.add(new String[]{"give", "gave", "given"});
        irregularWords.add(new String[]{"go", "went", "gone"});
        irregularWords.add(new String[]{"grow", "grew", "grown"});
        irregularWords.add(new String[]{"hang", "hung", "hanged"});
        irregularWords.add(new String[]{"have", "has", "had"});
        irregularWords.add(new String[]{"hear", "heard"});
        irregularWords.add(new String[]{"hide", "hid", "hidden"});
        irregularWords.add(new String[]{"hold", "held"});
        irregularWords.add(new String[]{"keep", "kept"});
        irregularWords.add(new String[]{"kneel", "kneeled", "kne;t"});
        irregularWords.add(new String[]{"know", "knew", "known"});
        irregularWords.add(new String[]{"lay", "laid"});
        irregularWords.add(new String[]{"lead", "led"});
        irregularWords.add(new String[]{"lean", "leaned", "leant"});
        irregularWords.add(new String[]{"learn", "learned", "learnt"});
        irregularWords.add(new String[]{"leave", "left"});
        irregularWords.add(new String[]{"lie", "lay", "lain"});
        irregularWords.add(new String[]{"light", "lit", "lighted"});
        irregularWords.add(new String[]{"lose", "lost"});
        irregularWords.add(new String[]{"make", "made"});
        irregularWords.add(new String[]{"mean", "meant"});
        irregularWords.add(new String[]{"meet", "met"});
        irregularWords.add(new String[]{"pay", "paid"});
        irregularWords.add(new String[]{"plead", "pled", "pleaded"});
        irregularWords.add(new String[]{"prove", "proved", "proven"});
        irregularWords.add(new String[]{"ride", "rode", "ridden"});
        irregularWords.add(new String[]{"rise", "rose", "risen"});
        irregularWords.add(new String[]{"say", "said"});
        irregularWords.add(new String[]{"see", "saw", "seen"});
        irregularWords.add(new String[]{"sell", "sold"});
        irregularWords.add(new String[]{"send", "sent"});
        irregularWords.add(new String[]{"sew", "sewed", "sewn"});
        irregularWords.add(new String[]{"shake", "shook", "shaken"});
        irregularWords.add(new String[]{"shine", "shone", "shined"});
        irregularWords.add(new String[]{"shoot", "shot"});
        irregularWords.add(new String[]{"show", "showed", "shown"});
        irregularWords.add(new String[]{"sing", "sang", "sung"});
        irregularWords.add(new String[]{"sink", "sank", "sunk"});
        irregularWords.add(new String[]{"sit", "sat"});
        irregularWords.add(new String[]{"sleep", "slept"});
        irregularWords.add(new String[]{"sling", "slung"});
        irregularWords.add(new String[]{"smell", "smelled", "selt"});
        irregularWords.add(new String[]{"speak", "spoke", "spoken"});
        irregularWords.add(new String[]{"spill", "spilled", "spilt"});
        irregularWords.add(new String[]{"spit", "spat"});
        irregularWords.add(new String[]{"stand", "stood"});
        irregularWords.add(new String[]{"steal", "stole", "stolen"});
        irregularWords.add(new String[]{"sting", "stung"});
        irregularWords.add(new String[]{"swear", "swore", "sworn"});
        irregularWords.add(new String[]{"sweat", "sweated"});
        irregularWords.add(new String[]{"sweep", "swept"});
        irregularWords.add(new String[]{"swim", "swam", "swum"});
        irregularWords.add(new String[]{"take", "took", "taken"});
        irregularWords.add(new String[]{"teach", "taught"});
        irregularWords.add(new String[]{"tear", "tore", "torn"});
        irregularWords.add(new String[]{"tell", "told"});
        irregularWords.add(new String[]{"think", "thought"});
        irregularWords.add(new String[]{"throw", "threw", "thrown"});
        irregularWords.add(new String[]{"wear", "wore", "worn"});
        irregularWords.add(new String[]{"wind", "wound"});
        irregularWords.add(new String[]{"write", "wrote", "written"});
        //Irregular plural forms of nouns
        irregularWords.add(new String[]{"child", "children"});
        irregularWords.add(new String[]{"man", "men"});
        irregularWords.add(new String[]{"woman", "women"});
        irregularWords.add(new String[]{"tooth", "teeth"});
        irregularWords.add(new String[]{"foot", "feet"});
        irregularWords.add(new String[]{"person", "people"});
    }
    
    public void clickWhyFound() {
         new Alert(Alert.AlertType.INFORMATION, "This program attempts to correct plural and verb " +
                 "form issues, and in doing so it may have found a word not intended "
                 + "(such as 'wifes').  TBTA is always the final answer as to whether or not "
                 + "a word can be included in Phase 1.").showAndWait();
    }
    public void clickWhyNotFound() {
        new Alert(Alert.AlertType.INFORMATION, "There are many oddities in the English language.  " +
                "While this program attempts to include as many forms of verbs and plurals as " +
                "possible, not all are accounted for.  You can try the original form of the word " +
                "('wife' instead of 'wives'), but TBTA is always the final answer as to whether or " +
                "not a word can be included in Phase 1.").showAndWait();
    }
    public void clickWhatAreSuggestions() {
        new Alert(Alert.AlertType.INFORMATION, "Using hyphens (like 'write-down' as opposed to " +
                "'write down' increases the meaning garnered by the analyzer in Phase 1.").showAndWait();
    }
    
    public void analyze() {
        //Reset views and info
        problems.setText(PROBLEMS_START);
        words.setText(WORDS_START);
        suggestions.setText(SUGGESTIONS_START);
        meanings.setText(MEANINGS_START); meaningsLocked = false;
        foundWords.clear();
        foundWords = new ArrayList<String>();
        foundWordMeanings.clear();
        foundWordMeanings = new ArrayList<ArrayList<String>>();
        
        //Do the words exist
        String whole = text.getText().replace("[", "").replace("]", "");
        String[] words = whole.split("[^a-zA-Z'()-]+");
        for (int i = 0; i < words.length; i++) {
            if (words[i] == null || words[i].length() == 0) continue;
            
            //Store information
            boolean found = false;
            String listLocation = "";
            int senses = 0;
            
            //Watch for posessive and pronouns
            String searchWord = words[i].replace("'s", "").replace("'", "");
            if (searchWord.contains("(")) searchWord = searchWord.substring
                   (0, searchWord.indexOf("("));
            String originalSearchWord = searchWord;
            
            //Keep track of changes to the word
            boolean ied = false, ing = false, ed = false, d = false, ses = false, ies = false, s = false;
            boolean reallyFound = false;
            
            //Try to find the word
            while (!found) {
                //If we actualy have been found but are running through again, revert to what is correct
                found = reallyFound;
                
                //Search through the word lists
                for (int j = 0; j < wordLists.length; j++) {
                    //Find word
                    int firstLocation = wordLists[j].indexOf(new Word(searchWord));
                    if (firstLocation >= 0) {
                        //Found
                        if (!found) {
                            foundWordMeanings.add(new ArrayList<String>());
                        }
                        found = true;
                        listLocation += tables.get(j).substring(0, tables.get(j).length() - 1);
                        if (listLocation.equals("Pronoun") &&
                                (!words[i].contains("(") || !words[i].contains(")"))) {
                            addToLabel(problems, "Pronoun " + words[i]);
                        }
                        int newSenses = wordLists[j].lastIndexOf(new Word(searchWord)) - firstLocation + 1;
                        for (int k = firstLocation; k < firstLocation + newSenses; k++) {
                            foundWordMeanings.get(foundWordMeanings.size() - 1).add(wordLists[j]
                                    .get(k).meaning);
                        }
                        senses += newSenses;
                    }
                }
                
                //Change to lower case
                if (!searchWord.equals(searchWord.toLowerCase())) {
                    searchWord = searchWord.toLowerCase();
                    reallyFound = found;
                    found = false;
                    continue;
                }
                
                if (!found) {
                    boolean irregularChange = false;
                    //Irregular words
                    for (int j = 0; j < irregularWords.size(); j++) {
                        for (int k = 0; k < irregularWords.get(j).length; k++) {
                            if (irregularWords.get(j)[k].equals(searchWord)) {
                                searchWord = irregularWords.get(j)[0];
                                irregularChange = true;
                            }
                        }
                    }
                    if (irregularChange) continue;
                }
                
                //Try verb forms
                if (!found) {
                    //Regular verbs
                    try {
                        //cry -> cried and other such verbs
                        if (originalSearchWord.substring(originalSearchWord.length() - 3).equals("ied")
                                && !ied) {
                            searchWord = originalSearchWord.substring(0, originalSearchWord.length() - 3) + "y";
                            ied = true; continue;
                        }
                    } catch (Exception e) {}
                    try {
                        //-ing verbs
                        if (originalSearchWord.substring(originalSearchWord.length() - 3).equals("ing")
                                && !ing) {
                            searchWord = originalSearchWord.substring(0, originalSearchWord.length() - 3);
                            ing = true; continue;
                        }
                    } catch (Exception e) {}
                    try {
                        //-ed verbs
                        if (originalSearchWord.substring(originalSearchWord.length() - 2).equals("ed")
                                && !ed) {
                            searchWord = originalSearchWord.substring(0, originalSearchWord.length() - 2);
                            ed = true; continue;
                        }
                    } catch (Exception e) {}
                    try {
                        //-d verbs
                        if (originalSearchWord.substring(originalSearchWord.length() - 1).equals("d")
                                && !d) {
                            searchWord = originalSearchWord.substring(0, originalSearchWord.length() - 1);
                            d = true; continue;
                        }
                    } catch (Exception e) {}
                }
                
                //Try plural
                if (!found) {
                    try {
                        //adding es
                        if (originalSearchWord.substring(originalSearchWord.length() - 3).equals("ses") ||
                                originalSearchWord.substring(originalSearchWord.length() - 3).equals("zes") ||
                                originalSearchWord.substring(originalSearchWord.length() - 3).equals("xes") ||
                                originalSearchWord.substring(originalSearchWord.length() - 4).equals("shes") ||
                                originalSearchWord.substring(originalSearchWord.length() - 4).equals("ches")
                                && !ses) {
                            searchWord = originalSearchWord.substring(0, originalSearchWord.length() - 2);
                            ses = true; continue;
                        }
                    } catch (Exception e) {}
                    try {
                        //replacing -ies with -y.  Works for plural and some verbs
                        if (originalSearchWord.substring(originalSearchWord.length() - 3).equals("ies")
                                && !ies) {
                            searchWord = originalSearchWord.substring(0, originalSearchWord.length() - 3) + "y";
                            ies = true; continue;
                        }
                    } catch (Exception e) {}
                    try {
                        //adding an s
                        if (originalSearchWord.substring(originalSearchWord.length() - 1).equals("s")
                                && !s) {
                            searchWord = originalSearchWord.substring(0, originalSearchWord.length() - 1);
                            s = true; continue;
                        }
                    } catch (Exception e) {}
                }
                
                //No find or changes; give up
                break;
            }
            
            //Act if the word is or is not found
            if (!found) {
                addToLabel(problems, words[i] + " not found.");
            } else {
                addToLabel(this.words, words[i] + ": " + listLocation + "; " + senses);
                foundWords.add(words[i]);
            }
        }
        
        //Brackets
        boolean moreRight = false;
        int leftBracket = 0, rightBracket = 0;
        for (int i = 0; i < text.getText().length(); i++) {
            if (text.getText().charAt(i) == '[') leftBracket++;
            if (text.getText().charAt(i) == ']') rightBracket++;
            if (rightBracket > leftBracket) moreRight = true;
        }
        if (leftBracket > rightBracket) addToLabel(problems, "Not enough right brackets.");
        if (rightBracket > leftBracket) {
            addToLabel(problems, "Not enough left brackets.");
        } else if (moreRight) {
            addToLabel(problems, "Bracket problem");
        }

        //Hyphenated phrases, for suggestions
        for (int i = 0; i < hyphenated.size(); i++) {
            if (text.getText().toLowerCase().contains(dehyphenated.get(i))) {
                addToLabel(suggestions, hyphenated.get(i));
            }
        }
    }
    
    public void mouseWords(double y) {
        //Update the meanings with the senses of the word that is moused over
        if (meaningsLocked) return;
        int word = (int)((foundWords.size() + 1) * (y) / (words.getHeight())) - 1;
        if (word >= 0) {
            try {
                meanings.setText(foundWords.get(word) + ":\n");
                //Put meanings in the label
                for (int i = 0; i < foundWordMeanings.get(word).size(); i++) {
                    addToLabel(meanings, "*" + foundWordMeanings.get(word).get(i));
                }
            } catch (Exception e) {}
        } else {
            meanings.setText(MEANINGS_START);
        }
    }
    public void clickWords() {
        //Toggles the locked setting for meanings
        meaningsLocked = !meaningsLocked;
    }
    public void mouseExitWords() {
        //If not locked, clear the display of meanings
        if (!meaningsLocked) meanings.setText(MEANINGS_START);
    }
    public void setWidth(Region region, double width) {
        region.setMinWidth(width);
        region.setMaxWidth(width);
    }
    public void setHeight(Region region, double height) {
        region.setMinHeight(height);
        region.setMaxHeight(height);
    }
    public void setDim(Region region, double width, double height) {
        setWidth(region, width);
        setHeight(region, height);
    }
    public void addToLabel(Label label, String text) {
        //Adds a line of text to a label
        label.setText(label.getText() + text + "\n");
    }
}
