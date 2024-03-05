package org.ferris.resiste.console.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class RegExForImgTagTest {

    @Test
    public void testLinkedList() {
        String html = "<p><a href=\"https://mars.nasa.gov/news/9549/\">\n" +
                      "<img src=\"https://mars.nasa.gov/system/news_items/main_images/9549_PIA24925_MAIN.jpg\" \n   style=\"padding-right:10px; \n padding-bottom:5px;\" \n align=\"left\" \n alt=\"Read article: Team Assessing SHERLOC Instrument on NASA's Perseverance Rover\" \n width=\"100\" \n height=\"75\" \n border=\"0\" /></a><br /><img src='' height='5' foo='bar' width=9></img><br />Engineers are working to stabilize a dust cover on one of the science instrument’s cameras.</p><img src=\"f\"   width=''/><br clear=\"all\"/><img src='b' height=5></img><br /><img <";

        String expected = "<p><a href=\"https://mars.nasa.gov/news/9549/\">\n" +
                      "<img src=\"https://mars.nasa.gov/system/news_items/main_images/9549_PIA24925_MAIN.jpg\" \n   style=\"padding-right:10px; \n padding-bottom:5px;\" \n align=\"left\" \n alt=\"Read article: Team Assessing SHERLOC Instrument on NASA's Perseverance Rover\" \n resistewidth=\"100\" \n resisteheight=\"75\" \n border=\"0\" /></a><br /><img src='' resisteheight='5' foo='bar' resistewidth=9></img><br />Engineers are working to stabilize a dust cover on one of the science instrument’s cameras.</p><img src=\"f\"   resistewidth=''/><br clear=\"all\"/><img src='b' resisteheight=5></img><br /><img <";
        char [] htmlArr 
            = html.toCharArray();
        
        // DO IT ALL
        List<Img> theTags
            = new LinkedList<>();
        for (int begin=0; begin<htmlArr.length; begin++) {
            if (htmlArr[begin] == '<') {
                String tag = html.substring(begin, Math.min(begin+4, html.length())).toLowerCase();
                if (tag.equalsIgnoreCase("<img")) {
                    int end = html.indexOf('>', begin);
                    if (end != -1) {
                        String contents 
                            = html.substring(begin, Math.min(end + 1, html.length()));
                        if (contents != null) {
                            int h = contents.toLowerCase().indexOf("height");
                            int w = contents.toLowerCase().indexOf("width");
                            if (h != -1) {
                                h = begin + h;
                            }
                            if (w != -1) {
                                w = begin + w;
                            }
                            if (h != -1 || w != -1) {
                                theTags.add(
                                    new Img(begin, end, w, h, contents)
                                );
                            }
                        }
                    }
                }
            }
        }
        System.out.printf("Count of <img tags = %d%n", theTags.size());
        theTags.stream().forEach(System.out::println);
        Assert.assertEquals(4, theTags.size());
        
        List<Integer> sortedIndexes 
            = theTags.stream()
                .map(t -> Arrays.asList(t.heightBegin, t.widthBegin))
                .flatMap(list -> list.stream())
                .filter(i -> i != -1)
                .sorted()
                .collect(Collectors.toList())
        ;
        //289, 303, 352, 373, 503, 543
        Assert.assertEquals(6, sortedIndexes.size());
        Assert.assertEquals(289, sortedIndexes.get(0).intValue());
        Assert.assertEquals(303, sortedIndexes.get(1).intValue());
        Assert.assertEquals(352, sortedIndexes.get(2).intValue());
        Assert.assertEquals(373, sortedIndexes.get(3).intValue());
        Assert.assertEquals(503, sortedIndexes.get(4).intValue());
        Assert.assertEquals(543, sortedIndexes.get(5).intValue());
        
        // Test I can re-create the orginal string
        {
            AtomicInteger index = new AtomicInteger(0);
            List<String> split = sortedIndexes.stream()
                    .map(end ->  html.substring(index.getAndSet(end), Math.min(end, html.length())))
                    .collect(Collectors.toList())
            ;
            split.add(html.substring(index.get()));
//            System.out.printf("%s%n",html);
//            System.out.printf("%s%n", String.join("", split));
            Assert.assertEquals(html, String.join("", split));
        } 
        
        // Test I can alter the height and width attributes
        {
            AtomicInteger index = new AtomicInteger(0);
            List<String> split = sortedIndexes.stream()
                    .map(end -> 
                            html.substring(index.getAndSet(end), Math.min(end, html.length()))
                            + "resiste"
                    ).collect(Collectors.toList())
            ;
            split.add(html.substring(index.get()));
//            System.out.printf("%s%n",html);
//            System.out.printf("%s%n", String.join("", split));
            Assert.assertEquals(expected, String.join("", split));
        }   
        
    }

    class Img {
        Integer tagBegin;
        Integer tagEnd;
        Integer widthBegin;
        Integer heightBegin;
        String contents;

        public Img(Integer tagBegin, Integer tagEnd, Integer widthBegin, Integer heightBegin, String contents) {
            this.tagBegin = tagBegin;
            this.tagEnd = tagEnd;
            this.widthBegin = widthBegin;
            this.heightBegin = heightBegin;
            this.contents = contents;
        }

        @Override
        public String toString() {
            StringJoiner sj = new StringJoiner(", ", "[Img ", "]");
            sj.add(String.format("tagBegin:%s", (tagBegin == null) ? "null" : tagBegin));
            sj.add(String.format("tagEnd:%s", (tagEnd == null) ? "null" : tagEnd));
            sj.add(String.format("widthBegin:%s", (widthBegin == null) ? "null" : widthBegin));
            sj.add(String.format("heightBegin:%s", (heightBegin == null) ? "null" : heightBegin));
            sj.add(String.format("contents:\"%s\"", contents));
            return sj.toString();
        }
    }
}
