package com.mobiquity.packer.repository;

import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.KnapsackProblem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parse a line in the following format and return a KnapsackProblem object:
 * 81.444232323 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9)
 *
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public class RegexProblemParser implements ProblemParser {
    private static final String decimalNo = "\\s*((?:\\d*\\.)?\\d+)\\s*";
    private static final String repeatingPattern = "\\s*\\(\\s*(\\d+)\\s*,(" + decimalNo + "),\\s*€(" + decimalNo + ")\\)";

    private static final Pattern compileFirst = Pattern.compile(decimalNo + ":(" + repeatingPattern + "\\s*)(" + repeatingPattern + "\\s*)*");
    private static final Pattern compileFollowing = Pattern.compile("\\)(" + repeatingPattern + ")");

    @Override
    public KnapsackProblem parse(String line) throws ParseException {
        if (line == null || line.isEmpty()) throw new ParseException("Invalid line " + line);

        Matcher matcherFirst = compileFirst.matcher(line);
        Matcher matcherFollowing = compileFollowing.matcher(line);

        if (!matcherFirst.matches()) {
            throw new ParseException("Can't parse " + line);
        }

        ArrayList<Item> items = new ArrayList<>();
        BigDecimal maxWeight;
        try {
            maxWeight = new BigDecimal(matcherFirst.group(1));
        } catch (Exception e) {
            throw new ParseException("Can't parse max allowed weight " + matcherFirst.group(1) + " on line " + line);
        }

        items.add(createItem(matcherFirst.group(3), matcherFirst.group(5), matcherFirst.group(6), line));

        int start = 0;
        while (matcherFollowing.find(start)) {
            items.add(createItem(matcherFollowing.group(2), matcherFollowing.group(4), matcherFollowing.group(5), line));
            start = matcherFollowing.end() - 1;
        }

        return new KnapsackProblem(maxWeight, items);
    }

    private Item createItem(String indexStr, String weightStr, String costStr, String line) throws ParseException {
        Integer index;
        try {
            index = Integer.valueOf(indexStr.trim());
        } catch (Exception e) {
            throw new ParseException("Can't parse index " + indexStr + " on line " + line);
        }

        BigDecimal weight;
        try {
            weight = new BigDecimal(weightStr.trim());
            if (weight.compareTo(BigDecimal.ZERO) < 1) throw new ParseException("Weight should be greater than 0");
        } catch (Exception e) {
            throw new ParseException("Can't parse weight " + weightStr + " on line " + line);
        }

        BigDecimal cost;
        try {
            cost = new BigDecimal(costStr.trim());
        } catch (Exception e) {
            throw new ParseException("Can't parse cost " + costStr + " on line " + line);
        }

        return new Item(index, weight, cost);
    }

}
