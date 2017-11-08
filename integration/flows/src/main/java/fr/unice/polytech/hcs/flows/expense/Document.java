package fr.unice.polytech.hcs.flows.expense;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Document {
    @JsonProperty public String category;
    @JsonProperty public String evidence;
    @JsonProperty public Integer price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document document = (Document) o;

        if (price != document.price) return false;
        if (category != null ? !category.equals(document.category) : document.category != null) return false;
        return evidence != null ? evidence.equals(document.evidence) : document.evidence == null;
    }

    @Override
    public int hashCode() {
        int result = category != null ? category.hashCode() : 0;
        result = 31 * result + (evidence != null ? evidence.hashCode() : 0);
        result = 31 * result + price;
        return result;
    }
}
