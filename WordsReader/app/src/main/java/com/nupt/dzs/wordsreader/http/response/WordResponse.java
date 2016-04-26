package com.nupt.dzs.wordsreader.http.response;

import java.util.List;

/**
 * Created by dading on 2016/4/26.
 *
 * @version 1.0
 * @copyright by ${COMPANY}
 */
public class WordResponse {
    /**
     *  "en_definitions": {
     n: [
     "an expression of greeting"
     ]
     },
     "cn_definition": {
     "pos": "",
     "defn": "int.（见面打招呼或打电话用语）喂,哈罗"
     },
     "id": 3130,
     "retention": 4,
     "definition": " int.（见面打招呼或打电话用语）喂,哈罗",
     "target_retention": 5,
     "en_definition": {
     "pos": "n",
     "defn": "an expression of greeting"
     },
     "learning_id": 2915819690,
     "content": "hello",
     "pronunciation": "hә'lәu",
     "audio": "http://media.shanbay.com/audio/us/hello.mp3",
     "uk_audio": "http://media.shanbay.com/audio/uk/hello.mp3",
     "us_audio": "http://media.shanbay.com/audio/us/hello.mp3"
     */
    private long id;
    private int retention;
    private String definition;
    private int target_retention;
    private long learning_id;
    private String content;
    private String pronunciation;
    private Definition en_definition;
    private Definition cn_definition;
    private String audio;
    private String uk_audio;
    private String us_audio;
    private Definitions en_definitions;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRetention() {
        return retention;
    }

    public void setRetention(int retention) {
        this.retention = retention;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public int getTarget_retention() {
        return target_retention;
    }

    public void setTarget_retention(int target_retention) {
        this.target_retention = target_retention;
    }

    public long getLearning_id() {
        return learning_id;
    }

    public void setLearning_id(long learning_id) {
        this.learning_id = learning_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public Definition getEn_definition() {
        return en_definition;
    }

    public void setEn_definition(Definition en_definition) {
        this.en_definition = en_definition;
    }

    public Definition getCn_definition() {
        return cn_definition;
    }

    public void setCn_definition(Definition cn_definition) {
        this.cn_definition = cn_definition;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getUk_audio() {
        return uk_audio;
    }

    public void setUk_audio(String uk_audio) {
        this.uk_audio = uk_audio;
    }

    public String getUs_audio() {
        return us_audio;
    }

    public void setUs_audio(String us_audio) {
        this.us_audio = us_audio;
    }

    public Definitions getEn_definitions() {
        return en_definitions;
    }

    public void setEn_definitions(Definitions en_definitions) {
        this.en_definitions = en_definitions;
    }

    public class Definition {
        private String pos;
        private String defn;

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public String getDefn() {
            return defn;
        }

        public void setDefn(String defn) {
            this.defn = defn;
        }
    }

    public class Definitions{
        private List<String> n;
        private List<String> adj;
        private List<String> v;

        public List<String> getN() {
            return n;
        }

        public void setN(List<String> n) {
            this.n = n;
        }

        public List<String> getAdj() {
            return adj;
        }

        public void setAdj(List<String> adj) {
            this.adj = adj;
        }

        public List<String> getV() {
            return v;
        }

        public void setV(List<String> v) {
            this.v = v;
        }

        @Override
        public String toString() {
            return "Definitions{" +
                    "n=" + n +
                    ", adj=" + adj +
                    ", v=" + v +
                    '}';
        }
    }
}
