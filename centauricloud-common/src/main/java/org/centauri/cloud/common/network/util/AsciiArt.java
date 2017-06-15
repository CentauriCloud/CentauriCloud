package org.centauri.cloud.common.network.util;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AsciiArt {


	private String fromText(char[] text) {
		List<List<String>> asciiLetters = new ArrayList<>();
		for (char character : text) {
			AsciiLetters letter;
			switch (character) {
				case ' ':
					letter = AsciiLetters.SPACE;
					break;
				case '?':
					letter = AsciiLetters.QUESTION;
					break;
				case '!':
					letter = AsciiLetters.EXCLAMATIONMARK;
					break;
				case '.':
					letter = AsciiLetters.POINT;
					break;
				case ',':
					letter = AsciiLetters.COMMA;
					break;
				case ':':
					letter = AsciiLetters.DOUBLEPOINT;
					break;
				case '(':
					letter = AsciiLetters.BRACKETROUNDLEFT;
					break;
				case ')':
					letter = AsciiLetters.BRACKETROUNDRIGHT;
					break;
				case '{':
					letter = AsciiLetters.BRACKETROUNDLEFT;
					break;
				case '}':
					letter = AsciiLetters.BRACKETCODINGRIGHT;
					break;
				default:
					letter = AsciiLetters.valueOf(character + "");
					break;
			}
			asciiLetters.add(Arrays.asList(letter.getAsciiArt().split("\n")));
		}
		int height = asciiLetters.get(0).size();
		StringBuilder stringBuilder = new StringBuilder();
		for (int line = 0; line < height; line++) {
			for (List<String> asciiLetter : asciiLetters) {
				String textLine = asciiLetter.get(line);
				stringBuilder.append(textLine);
			}
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

	public String fromText(String letters) {
		StringBuilder finalText = new StringBuilder();
		String[] newLines = letters.split("\n");
		for (String oneLine : newLines) {
			finalText.append(fromText(oneLine.toCharArray())).append("\n");
		}
		return finalText.toString();
	}


	public enum AsciiLetters {
		a("                  \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"  aaaaaaaaaaaaa   \n" +
				"  a::::::::::::a  \n" +
				"  aaaaaaaaa:::::a \n" +
				"           a::::a \n" +
				"    aaaaaaa:::::a \n" +
				"  aa::::::::::::a \n" +
				" a::::aaaa::::::a \n" +
				"a::::a    a:::::a \n" +
				"a::::a    a:::::a \n" +
				"a:::::aaaa::::::a \n" +
				" a::::::::::aa:::a\n" +
				"  aaaaaaaaaa  aaaa\n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  "),
		b("                    \n" +
				"bbbbbbbb            \n" +
				"b::::::b            \n" +
				"b::::::b            \n" +
				"b::::::b            \n" +
				" b:::::b            \n" +
				" b:::::bbbbbbbbb    \n" +
				" b::::::::::::::bb  \n" +
				" b::::::::::::::::b \n" +
				" b:::::bbbbb:::::::b\n" +
				" b:::::b    b::::::b\n" +
				" b:::::b     b:::::b\n" +
				" b:::::b     b:::::b\n" +
				" b:::::b     b:::::b\n" +
				" b:::::bbbbbb::::::b\n" +
				" b::::::::::::::::b \n" +
				" b:::::::::::::::b  \n" +
				" bbbbbbbbbbbbbbbb   \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    "),
		c("                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"    cccccccccccccccc\n" +
				"  cc:::::::::::::::c\n" +
				" c:::::::::::::::::c\n" +
				"c:::::::cccccc:::::c\n" +
				"c::::::c     ccccccc\n" +
				"c:::::c             \n" +
				"c:::::c             \n" +
				"c::::::c     ccccccc\n" +
				"c:::::::cccccc:::::c\n" +
				" c:::::::::::::::::c\n" +
				"  cc:::::::::::::::c\n" +
				"    cccccccccccccccc\n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    "),
		d("                    \n" +
				"            dddddddd\n" +
				"            d::::::d\n" +
				"            d::::::d\n" +
				"            d::::::d\n" +
				"            d:::::d \n" +
				"    ddddddddd:::::d \n" +
				"  dd::::::::::::::d \n" +
				" d::::::::::::::::d \n" +
				"d:::::::ddddd:::::d \n" +
				"d::::::d    d:::::d \n" +
				"d:::::d     d:::::d \n" +
				"d:::::d     d:::::d \n" +
				"d:::::d     d:::::d \n" +
				"d::::::ddddd::::::dd\n" +
				" d:::::::::::::::::d\n" +
				"  d:::::::::ddd::::d\n" +
				"   ddddddddd   ddddd\n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    "),
		e("                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"    eeeeeeeeeeee    \n" +
				"  ee::::::::::::ee  \n" +
				" e::::::eeeee:::::ee\n" +
				"e::::::e     e:::::e\n" +
				"e:::::::eeeee::::::e\n" +
				"e:::::::::::::::::e \n" +
				"e::::::eeeeeeeeeee  \n" +
				"e:::::::e           \n" +
				"e::::::::e          \n" +
				" e::::::::eeeeeeee  \n" +
				"  ee:::::::::::::e  \n" +
				"    eeeeeeeeeeeeee  \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    "),
		f("                      \n" +
				"                      \n" +
				"    ffffffffffffffff  \n" +
				"   f::::::::::::::::f \n" +
				"  f::::::::::::::::::f\n" +
				"  f::::::fffffff:::::f\n" +
				"  f:::::f       ffffff\n" +
				"  f:::::f             \n" +
				" f:::::::ffffff       \n" +
				" f::::::::::::f       \n" +
				" f::::::::::::f       \n" +
				" f:::::::ffffff       \n" +
				"  f:::::f             \n" +
				"  f:::::f             \n" +
				" f:::::::f            \n" +
				" f:::::::f            \n" +
				" f:::::::f            \n" +
				" fffffffff            \n" +
				"                      \n" +
				"                      \n" +
				"                      \n" +
				"                      \n" +
				"                      \n" +
				"                      \n" +
				"                      "),
		g("                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"   ggggggggg   ggggg\n" +
				"  g:::::::::ggg::::g\n" +
				" g:::::::::::::::::g\n" +
				"g::::::ggggg::::::gg\n" +
				"g:::::g     g:::::g \n" +
				"g:::::g     g:::::g \n" +
				"g:::::g     g:::::g \n" +
				"g::::::g    g:::::g \n" +
				"g:::::::ggggg:::::g \n" +
				" g::::::::::::::::g \n" +
				"  gg::::::::::::::g \n" +
				"    gggggggg::::::g \n" +
				"            g:::::g \n" +
				"gggggg      g:::::g \n" +
				"g:::::gg   gg:::::g \n" +
				" g::::::ggg:::::::g \n" +
				"  gg:::::::::::::g  \n" +
				"    ggg::::::ggg    \n" +
				"       gggggg       "),
		h("                    \n" +
				"                    \n" +
				"hhhhhhh             \n" +
				"h:::::h             \n" +
				"h:::::h             \n" +
				"h:::::h             \n" +
				" h::::h hhhhh       \n" +
				" h::::hh:::::hhh    \n" +
				" h::::::::::::::hh  \n" +
				" h:::::::hhh::::::h \n" +
				" h::::::h   h::::::h\n" +
				" h:::::h     h:::::h\n" +
				" h:::::h     h:::::h\n" +
				" h:::::h     h:::::h\n" +
				" h:::::h     h:::::h\n" +
				" h:::::h     h:::::h\n" +
				" h:::::h     h:::::h\n" +
				" hhhhhhh     hhhhhhh\n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    "),
		i("        \n" +
				"        \n" +
				"  iiii  \n" +
				" i::::i \n" +
				"  iiii  \n" +
				"        \n" +
				"iiiiiii \n" +
				"i:::::i \n" +
				" i::::i \n" +
				" i::::i \n" +
				" i::::i \n" +
				" i::::i \n" +
				" i::::i \n" +
				" i::::i \n" +
				"i::::::i\n" +
				"i::::::i\n" +
				"i::::::i\n" +
				"iiiiiiii\n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        "),
		j("                  \n" +
				"                  \n" +
				"             jjjj \n" +
				"            j::::j\n" +
				"             jjjj \n" +
				"                  \n" +
				"           jjjjjjj\n" +
				"           j:::::j\n" +
				"            j::::j\n" +
				"            j::::j\n" +
				"            j::::j\n" +
				"            j::::j\n" +
				"            j::::j\n" +
				"            j::::j\n" +
				"            j::::j\n" +
				"            j::::j\n" +
				"            j::::j\n" +
				"            j::::j\n" +
				"            j::::j\n" +
				"  jjjj      j::::j\n" +
				" j::::jj   j:::::j\n" +
				" j::::::jjj::::::j\n" +
				"  jj::::::::::::j \n" +
				"    jjj::::::jjj  \n" +
				"       jjjjjj     "),
		k("                   \n" +
				"                   \n" +
				"kkkkkkkk           \n" +
				"k::::::k           \n" +
				"k::::::k           \n" +
				"k::::::k           \n" +
				" k:::::k    kkkkkkk\n" +
				" k:::::k   k:::::k \n" +
				" k:::::k  k:::::k  \n" +
				" k:::::k k:::::k   \n" +
				" k::::::k:::::k    \n" +
				" k:::::::::::k     \n" +
				" k:::::::::::k     \n" +
				" k::::::k:::::k    \n" +
				"k::::::k k:::::k   \n" +
				"k::::::k  k:::::k  \n" +
				"k::::::k   k:::::k \n" +
				"kkkkkkkk    kkkkkkk\n" +
				"                   \n" +
				"                   \n" +
				"                   \n" +
				"                   \n" +
				"                   \n" +
				"                   \n" +
				"                   "),
		l("        \n" +
				"        \n" +
				"lllllll \n" +
				"l:::::l \n" +
				"l:::::l \n" +
				"l:::::l \n" +
				" l::::l \n" +
				" l::::l \n" +
				" l::::l \n" +
				" l::::l \n" +
				" l::::l \n" +
				" l::::l \n" +
				" l::::l \n" +
				" l::::l \n" +
				"l::::::l\n" +
				"l::::::l\n" +
				"l::::::l\n" +
				"llllllll\n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        "),
		m("                        \n" +
				"                        \n" +
				"                        \n" +
				"                        \n" +
				"                        \n" +
				"                        \n" +
				"   mmmmmmm    mmmmmmm   \n" +
				" mm:::::::m  m:::::::mm \n" +
				"m::::::::::mm::::::::::m\n" +
				"m::::::::::::::::::::::m\n" +
				"m:::::mmm::::::mmm:::::m\n" +
				"m::::m   m::::m   m::::m\n" +
				"m::::m   m::::m   m::::m\n" +
				"m::::m   m::::m   m::::m\n" +
				"m::::m   m::::m   m::::m\n" +
				"m::::m   m::::m   m::::m\n" +
				"m::::m   m::::m   m::::m\n" +
				"mmmmmm   mmmmmm   mmmmmm\n" +
				"                        \n" +
				"                        \n" +
				"                        \n" +
				"                        \n" +
				"                        \n" +
				"                        \n" +
				"                        "),
		n("                  \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"nnnn  nnnnnnnn    \n" +
				"n:::nn::::::::nn  \n" +
				"n::::::::::::::nn \n" +
				"nn:::::::::::::::n\n" +
				"  n:::::nnnn:::::n\n" +
				"  n::::n    n::::n\n" +
				"  n::::n    n::::n\n" +
				"  n::::n    n::::n\n" +
				"  n::::n    n::::n\n" +
				"  n::::n    n::::n\n" +
				"  n::::n    n::::n\n" +
				"  nnnnnn    nnnnnn\n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  "),
		o("                 \n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"   ooooooooooo   \n" +
				" oo:::::::::::oo \n" +
				"o:::::::::::::::o\n" +
				"o:::::ooooo:::::o\n" +
				"o::::o     o::::o\n" +
				"o::::o     o::::o\n" +
				"o::::o     o::::o\n" +
				"o::::o     o::::o\n" +
				"o:::::ooooo:::::o\n" +
				"o:::::::::::::::o\n" +
				" oo:::::::::::oo \n" +
				"   ooooooooooo   \n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"                 "),
		p("                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"ppppp   ppppppppp   \n" +
				"p::::ppp:::::::::p  \n" +
				"p:::::::::::::::::p \n" +
				"pp::::::ppppp::::::p\n" +
				" p:::::p     p:::::p\n" +
				" p:::::p     p:::::p\n" +
				" p:::::p     p:::::p\n" +
				" p:::::p    p::::::p\n" +
				" p:::::ppppp:::::::p\n" +
				" p::::::::::::::::p \n" +
				" p::::::::::::::pp  \n" +
				" p::::::pppppppp    \n" +
				" p:::::p            \n" +
				" p:::::p            \n" +
				"p:::::::p           \n" +
				"p:::::::p           \n" +
				"p:::::::p           \n" +
				"ppppppppp           \n" +
				"                    "),
		q("                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"   qqqqqqqqq   qqqqq\n" +
				"  q:::::::::qqq::::q\n" +
				" q:::::::::::::::::q\n" +
				"q::::::qqqqq::::::qq\n" +
				"q:::::q     q:::::q \n" +
				"q:::::q     q:::::q \n" +
				"q:::::q     q:::::q \n" +
				"q::::::q    q:::::q \n" +
				"q:::::::qqqqq:::::q \n" +
				" q::::::::::::::::q \n" +
				"  qq::::::::::::::q \n" +
				"    qqqqqqqq::::::q \n" +
				"            q:::::q \n" +
				"            q:::::q \n" +
				"           q:::::::q\n" +
				"           q:::::::q\n" +
				"           q:::::::q\n" +
				"           qqqqqqqqq\n" +
				"                    "),
		r("                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"rrrrr   rrrrrrrrr   \n" +
				"r::::rrr:::::::::r  \n" +
				"r:::::::::::::::::r \n" +
				"rr::::::rrrrr::::::r\n" +
				" r:::::r     r:::::r\n" +
				" r:::::r     rrrrrrr\n" +
				" r:::::r            \n" +
				" r:::::r            \n" +
				" r:::::r            \n" +
				" r:::::r            \n" +
				" r:::::r            \n" +
				" rrrrrrr            \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    "),
		s("                 \n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"    ssssssssss   \n" +
				"  ss::::::::::s  \n" +
				"ss:::::::::::::s \n" +
				"s::::::ssss:::::s\n" +
				" s:::::s  ssssss \n" +
				"   s::::::s      \n" +
				"      s::::::s   \n" +
				"ssssss   s:::::s \n" +
				"s:::::ssss::::::s\n" +
				"s::::::::::::::s \n" +
				" s:::::::::::ss  \n" +
				"  sssssssssss    \n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"                 "),
		t("                       \n" +
				"                       \n" +
				"         tttt          \n" +
				"      ttt:::t          \n" +
				"      t:::::t          \n" +
				"      t:::::t          \n" +
				"ttttttt:::::ttttttt    \n" +
				"t:::::::::::::::::t    \n" +
				"t:::::::::::::::::t    \n" +
				"tttttt:::::::tttttt    \n" +
				"      t:::::t          \n" +
				"      t:::::t          \n" +
				"      t:::::t          \n" +
				"      t:::::t    tttttt\n" +
				"      t::::::tttt:::::t\n" +
				"      tt::::::::::::::t\n" +
				"        tt:::::::::::tt\n" +
				"          ttttttttttt  \n" +
				"                       \n" +
				"                       \n" +
				"                       \n" +
				"                       \n" +
				"                       \n" +
				"                       \n" +
				"                       "),
		u("                  \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"uuuuuu    uuuuuu  \n" +
				"u::::u    u::::u  \n" +
				"u::::u    u::::u  \n" +
				"u::::u    u::::u  \n" +
				"u::::u    u::::u  \n" +
				"u::::u    u::::u  \n" +
				"u::::u    u::::u  \n" +
				"u:::::uuuu:::::u  \n" +
				"u:::::::::::::::uu\n" +
				" u:::::::::::::::u\n" +
				"  uu::::::::uu:::u\n" +
				"    uuuuuuuu  uuuu\n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  "),
		v("                         \n" +
				"                         \n" +
				"                         \n" +
				"                         \n" +
				"                         \n" +
				"                         \n" +
				"vvvvvvv           vvvvvvv\n" +
				" v:::::v         v:::::v \n" +
				"  v:::::v       v:::::v  \n" +
				"   v:::::v     v:::::v   \n" +
				"    v:::::v   v:::::v    \n" +
				"     v:::::v v:::::v     \n" +
				"      v:::::v:::::v      \n" +
				"       v:::::::::v       \n" +
				"        v:::::::v        \n" +
				"         v:::::v         \n" +
				"          v:::v          \n" +
				"           vvv           \n" +
				"                         \n" +
				"                         \n" +
				"                         \n" +
				"                         \n" +
				"                         \n" +
				"                         \n" +
				"                         "),
		w("                                         \n" +
				"                                         \n" +
				"                                         \n" +
				"                                         \n" +
				"                                         \n" +
				"                                         \n" +
				"wwwwwww           wwwww           wwwwwww\n" +
				" w:::::w         w:::::w         w:::::w \n" +
				"  w:::::w       w:::::::w       w:::::w  \n" +
				"   w:::::w     w:::::::::w     w:::::w   \n" +
				"    w:::::w   w:::::w:::::w   w:::::w    \n" +
				"     w:::::w w:::::w w:::::w w:::::w     \n" +
				"      w:::::w:::::w   w:::::w:::::w      \n" +
				"       w:::::::::w     w:::::::::w       \n" +
				"        w:::::::w       w:::::::w        \n" +
				"         w:::::w         w:::::w         \n" +
				"          w:::w           w:::w          \n" +
				"           www             www           \n" +
				"                                         \n" +
				"                                         \n" +
				"                                         \n" +
				"                                         \n" +
				"                                         \n" +
				"                                         \n" +
				"                                         "),
		x("                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"xxxxxxx      xxxxxxx\n" +
				" x:::::x    x:::::x \n" +
				"  x:::::x  x:::::x  \n" +
				"   x:::::xx:::::x   \n" +
				"    x::::::::::x    \n" +
				"     x::::::::x     \n" +
				"     x::::::::x     \n" +
				"    x::::::::::x    \n" +
				"   x:::::xx:::::x   \n" +
				"  x:::::x  x:::::x  \n" +
				" x:::::x    x:::::x \n" +
				"xxxxxxx      xxxxxxx\n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    "),
		y("                         \n" +
				"                         \n" +
				"                         \n" +
				"                         \n" +
				"                         \n" +
				"                         \n" +
				"yyyyyyy           yyyyyyy\n" +
				" y:::::y         y:::::y \n" +
				"  y:::::y       y:::::y  \n" +
				"   y:::::y     y:::::y   \n" +
				"    y:::::y   y:::::y    \n" +
				"     y:::::y y:::::y     \n" +
				"      y:::::y:::::y      \n" +
				"       y:::::::::y       \n" +
				"        y:::::::y        \n" +
				"         y:::::y         \n" +
				"        y:::::y          \n" +
				"       y:::::y           \n" +
				"      y:::::y            \n" +
				"     y:::::y             \n" +
				"    y:::::y              \n" +
				"   y:::::y               \n" +
				"  yyyyyyy                \n" +
				"                         \n" +
				"                         "),
		z("                 \n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"zzzzzzzzzzzzzzzzz\n" +
				"z:::::::::::::::z\n" +
				"z::::::::::::::z \n" +
				"zzzzzzzz::::::z  \n" +
				"      z::::::z   \n" +
				"     z::::::z    \n" +
				"    z::::::z     \n" +
				"   z::::::z      \n" +
				"  z::::::zzzzzzzz\n" +
				" z::::::::::::::z\n" +
				"z:::::::::::::::z\n" +
				"zzzzzzzzzzzzzzzzz\n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"                 \n" +
				"                 "),
		A("                                 \n" +
				"                                 \n" +
				"               AAA               \n" +
				"              A:::A              \n" +
				"             A:::::A             \n" +
				"            A:::::::A            \n" +
				"           A:::::::::A           \n" +
				"          A:::::A:::::A          \n" +
				"         A:::::A A:::::A         \n" +
				"        A:::::A   A:::::A        \n" +
				"       A:::::A     A:::::A       \n" +
				"      A:::::AAAAAAAAA:::::A      \n" +
				"     A:::::::::::::::::::::A     \n" +
				"    A:::::AAAAAAAAAAAAA:::::A    \n" +
				"   A:::::A             A:::::A   \n" +
				"  A:::::A               A:::::A  \n" +
				" A:::::A                 A:::::A \n" +
				"AAAAAAA                   AAAAAAA\n" +
				"                                 \n" +
				"                                 \n" +
				"                                 \n" +
				"                                 \n" +
				"                                 \n" +
				"                                 \n" +
				"                                 "),
		B("                    \n" +
				"                    \n" +
				"BBBBBBBBBBBBBBBBB   \n" +
				"B::::::::::::::::B  \n" +
				"B::::::BBBBBB:::::B \n" +
				"BB:::::B     B:::::B\n" +
				"  B::::B     B:::::B\n" +
				"  B::::B     B:::::B\n" +
				"  B::::BBBBBB:::::B \n" +
				"  B:::::::::::::BB  \n" +
				"  B::::BBBBBB:::::B \n" +
				"  B::::B     B:::::B\n" +
				"  B::::B     B:::::B\n" +
				"  B::::B     B:::::B\n" +
				"BB:::::BBBBBB::::::B\n" +
				"B:::::::::::::::::B \n" +
				"B::::::::::::::::B  \n" +
				"BBBBBBBBBBBBBBBBB   \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    "),
		C("                     \n" +
				"                     \n" +
				"        CCCCCCCCCCCCC\n" +
				"     CCC::::::::::::C\n" +
				"   CC:::::::::::::::C\n" +
				"  C:::::CCCCCCCC::::C\n" +
				" C:::::C       CCCCCC\n" +
				"C:::::C              \n" +
				"C:::::C              \n" +
				"C:::::C              \n" +
				"C:::::C              \n" +
				"C:::::C              \n" +
				"C:::::C              \n" +
				" C:::::C       CCCCCC\n" +
				"  C:::::CCCCCCCC::::C\n" +
				"   CC:::::::::::::::C\n" +
				"     CCC::::::::::::C\n" +
				"        CCCCCCCCCCCCC\n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     "),
		D("                     \n" +
				"                     \n" +
				"DDDDDDDDDDDDD        \n" +
				"D::::::::::::DDD     \n" +
				"D:::::::::::::::DD   \n" +
				"DDD:::::DDDDD:::::D  \n" +
				"  D:::::D    D:::::D \n" +
				"  D:::::D     D:::::D\n" +
				"  D:::::D     D:::::D\n" +
				"  D:::::D     D:::::D\n" +
				"  D:::::D     D:::::D\n" +
				"  D:::::D     D:::::D\n" +
				"  D:::::D     D:::::D\n" +
				"  D:::::D    D:::::D \n" +
				"DDD:::::DDDDD:::::D  \n" +
				"D:::::::::::::::DD   \n" +
				"D::::::::::::DDD     \n" +
				"DDDDDDDDDDDDD        \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     "),
		E("                      \n" +
				"                      \n" +
				"EEEEEEEEEEEEEEEEEEEEEE\n" +
				"E::::::::::::::::::::E\n" +
				"E::::::::::::::::::::E\n" +
				"EE::::::EEEEEEEEE::::E\n" +
				"  E:::::E       EEEEEE\n" +
				"  E:::::E             \n" +
				"  E::::::EEEEEEEEEE   \n" +
				"  E:::::::::::::::E   \n" +
				"  E:::::::::::::::E   \n" +
				"  E::::::EEEEEEEEEE   \n" +
				"  E:::::E             \n" +
				"  E:::::E       EEEEEE\n" +
				"EE::::::EEEEEEEE:::::E\n" +
				"E::::::::::::::::::::E\n" +
				"E::::::::::::::::::::E\n" +
				"EEEEEEEEEEEEEEEEEEEEEE\n" +
				"                      \n" +
				"                      \n" +
				"                      \n" +
				"                      \n" +
				"                      \n" +
				"                      \n" +
				"                      "),
		F("                      \n" +
				"                      \n" +
				"FFFFFFFFFFFFFFFFFFFFFF\n" +
				"F::::::::::::::::::::F\n" +
				"F::::::::::::::::::::F\n" +
				"FF::::::FFFFFFFFF::::F\n" +
				"  F:::::F       FFFFFF\n" +
				"  F:::::F             \n" +
				"  F::::::FFFFFFFFFF   \n" +
				"  F:::::::::::::::F   \n" +
				"  F:::::::::::::::F   \n" +
				"  F::::::FFFFFFFFFF   \n" +
				"  F:::::F             \n" +
				"  F:::::F             \n" +
				"FF:::::::FF           \n" +
				"F::::::::FF           \n" +
				"F::::::::FF           \n" +
				"FFFFFFFFFFF           \n" +
				"                      \n" +
				"                      \n" +
				"                      \n" +
				"                      \n" +
				"                      \n" +
				"                      \n" +
				"                      "),
		G("                     \n" +
				"                     \n" +
				"        GGGGGGGGGGGGG\n" +
				"     GGG::::::::::::G\n" +
				"   GG:::::::::::::::G\n" +
				"  G:::::GGGGGGGG::::G\n" +
				" G:::::G       GGGGGG\n" +
				"G:::::G              \n" +
				"G:::::G              \n" +
				"G:::::G    GGGGGGGGGG\n" +
				"G:::::G    G::::::::G\n" +
				"G:::::G    GGGGG::::G\n" +
				"G:::::G        G::::G\n" +
				" G:::::G       G::::G\n" +
				"  G:::::GGGGGGGG::::G\n" +
				"   GG:::::::::::::::G\n" +
				"     GGG::::::GGG:::G\n" +
				"        GGGGGG   GGGG\n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     "),
		H("                       \n" +
				"                       \n" +
				"HHHHHHHHH     HHHHHHHHH\n" +
				"H:::::::H     H:::::::H\n" +
				"H:::::::H     H:::::::H\n" +
				"HH::::::H     H::::::HH\n" +
				"  H:::::H     H:::::H  \n" +
				"  H:::::H     H:::::H  \n" +
				"  H::::::HHHHH::::::H  \n" +
				"  H:::::::::::::::::H  \n" +
				"  H:::::::::::::::::H  \n" +
				"  H::::::HHHHH::::::H  \n" +
				"  H:::::H     H:::::H  \n" +
				"  H:::::H     H:::::H  \n" +
				"HH::::::H     H::::::HH\n" +
				"H:::::::H     H:::::::H\n" +
				"H:::::::H     H:::::::H\n" +
				"HHHHHHHHH     HHHHHHHHH\n" +
				"                       \n" +
				"                       \n" +
				"                       \n" +
				"                       \n" +
				"                       \n" +
				"                       \n" +
				"                       "),
		I("          \n" +
				"          \n" +
				"IIIIIIIIII\n" +
				"I::::::::I\n" +
				"I::::::::I\n" +
				"II::::::II\n" +
				"  I::::I  \n" +
				"  I::::I  \n" +
				"  I::::I  \n" +
				"  I::::I  \n" +
				"  I::::I  \n" +
				"  I::::I  \n" +
				"  I::::I  \n" +
				"  I::::I  \n" +
				"II::::::II\n" +
				"I::::::::I\n" +
				"I::::::::I\n" +
				"IIIIIIIIII\n" +
				"          \n" +
				"          \n" +
				"          \n" +
				"          \n" +
				"          \n" +
				"          \n" +
				"          "),
		J("                     \n" +
				"                     \n" +
				"          JJJJJJJJJJJ\n" +
				"          J:::::::::J\n" +
				"          J:::::::::J\n" +
				"          JJ:::::::JJ\n" +
				"            J:::::J  \n" +
				"            J:::::J  \n" +
				"            J:::::J  \n" +
				"            J:::::j  \n" +
				"            J:::::J  \n" +
				"JJJJJJJ     J:::::J  \n" +
				"J:::::J     J:::::J  \n" +
				"J::::::J   J::::::J  \n" +
				"J:::::::JJJ:::::::J  \n" +
				" JJ:::::::::::::JJ   \n" +
				"   JJ:::::::::JJ     \n" +
				"     JJJJJJJJJ       \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     "),
		K("                    \n" +
				"                    \n" +
				"KKKKKKKKK    KKKKKKK\n" +
				"K:::::::K    K:::::K\n" +
				"K:::::::K    K:::::K\n" +
				"K:::::::K   K::::::K\n" +
				"KK::::::K  K:::::KKK\n" +
				"  K:::::K K:::::K   \n" +
				"  K::::::K:::::K    \n" +
				"  K:::::::::::K     \n" +
				"  K:::::::::::K     \n" +
				"  K::::::K:::::K    \n" +
				"  K:::::K K:::::K   \n" +
				"KK::::::K  K:::::KKK\n" +
				"K:::::::K   K::::::K\n" +
				"K:::::::K    K:::::K\n" +
				"K:::::::K    K:::::K\n" +
				"KKKKKKKKK    KKKKKKK\n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    "),
		L("                        \n" +
				"                        \n" +
				"LLLLLLLLLLL             \n" +
				"L:::::::::L             \n" +
				"L:::::::::L             \n" +
				"LL:::::::LL             \n" +
				"  L:::::L               \n" +
				"  L:::::L               \n" +
				"  L:::::L               \n" +
				"  L:::::L               \n" +
				"  L:::::L               \n" +
				"  L:::::L               \n" +
				"  L:::::L               \n" +
				"  L:::::L         LLLLLL\n" +
				"LL:::::::LLLLLLLLL:::::L\n" +
				"L::::::::::::::::::::::L\n" +
				"L::::::::::::::::::::::L\n" +
				"LLLLLLLLLLLLLLLLLLLLLLLL\n" +
				"                        \n" +
				"                        \n" +
				"                        \n" +
				"                        \n" +
				"                        \n" +
				"                        \n" +
				"                        "),
		M("                               \n" +
				"                               \n" +
				"MMMMMMMM               MMMMMMMM\n" +
				"M:::::::M             M:::::::M\n" +
				"M::::::::M           M::::::::M\n" +
				"M:::::::::M         M:::::::::M\n" +
				"M::::::::::M       M::::::::::M\n" +
				"M:::::::::::M     M:::::::::::M\n" +
				"M:::::::M::::M   M::::M:::::::M\n" +
				"M::::::M M::::M M::::M M::::::M\n" +
				"M::::::M  M::::M::::M  M::::::M\n" +
				"M::::::M   M:::::::M   M::::::M\n" +
				"M::::::M    M:::::M    M::::::M\n" +
				"M::::::M     MMMMM     M::::::M\n" +
				"M::::::M               M::::::M\n" +
				"M::::::M               M::::::M\n" +
				"M::::::M               M::::::M\n" +
				"MMMMMMMM               MMMMMMMM\n" +
				"                               \n" +
				"                               \n" +
				"                               \n" +
				"                               \n" +
				"                               \n" +
				"                               \n" +
				"                               "),
		N("                        \n" +
				"                        \n" +
				"NNNNNNNN        NNNNNNNN\n" +
				"N:::::::N       N::::::N\n" +
				"N::::::::N      N::::::N\n" +
				"N:::::::::N     N::::::N\n" +
				"N::::::::::N    N::::::N\n" +
				"N:::::::::::N   N::::::N\n" +
				"N:::::::N::::N  N::::::N\n" +
				"N::::::N N::::N N::::::N\n" +
				"N::::::N  N::::N:::::::N\n" +
				"N::::::N   N:::::::::::N\n" +
				"N::::::N    N::::::::::N\n" +
				"N::::::N     N:::::::::N\n" +
				"N::::::N      N::::::::N\n" +
				"N::::::N       N:::::::N\n" +
				"N::::::N        N::::::N\n" +
				"NNNNNNNN         NNNNNNN\n" +
				"                        \n" +
				"                        \n" +
				"                        \n" +
				"                        \n" +
				"                        \n" +
				"                        \n" +
				"                        "),
		O("                   \n" +
				"                   \n" +
				"     OOOOOOOOO     \n" +
				"   OO:::::::::OO   \n" +
				" OO:::::::::::::OO \n" +
				"O:::::::OOO:::::::O\n" +
				"O::::::O   O::::::O\n" +
				"O:::::O     O:::::O\n" +
				"O:::::O     O:::::O\n" +
				"O:::::O     O:::::O\n" +
				"O:::::O     O:::::O\n" +
				"O:::::O     O:::::O\n" +
				"O:::::O     O:::::O\n" +
				"O::::::O   O::::::O\n" +
				"O:::::::OOO:::::::O\n" +
				" OO:::::::::::::OO \n" +
				"   OO:::::::::OO   \n" +
				"     OOOOOOOOO     \n" +
				"                   \n" +
				"                   \n" +
				"                   \n" +
				"                   \n" +
				"                   \n" +
				"                   \n" +
				"                   "),
		P("                    \n" +
				"                    \n" +
				"PPPPPPPPPPPPPPPPP   \n" +
				"P::::::::::::::::P  \n" +
				"P::::::PPPPPP:::::P \n" +
				"PP:::::P     P:::::P\n" +
				"  P::::P     P:::::P\n" +
				"  P::::P     P:::::P\n" +
				"  P::::PPPPPP:::::P \n" +
				"  P:::::::::::::PP  \n" +
				"  P::::PPPPPPPPP    \n" +
				"  P::::P            \n" +
				"  P::::P            \n" +
				"  P::::P            \n" +
				"PP::::::PP          \n" +
				"P::::::::P          \n" +
				"P::::::::P          \n" +
				"PPPPPPPPPP          \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    "),
		Q("                    \n" +
				"                    \n" +
				"     QQQQQQQQQ      \n" +
				"   QQ:::::::::QQ    \n" +
				" QQ:::::::::::::QQ  \n" +
				"Q:::::::QQQ:::::::Q \n" +
				"Q::::::O   Q::::::Q \n" +
				"Q:::::O     Q:::::Q \n" +
				"Q:::::O     Q:::::Q \n" +
				"Q:::::O     Q:::::Q \n" +
				"Q:::::O     Q:::::Q \n" +
				"Q:::::O     Q:::::Q \n" +
				"Q:::::O  QQQQ:::::Q \n" +
				"Q::::::O Q::::::::Q \n" +
				"Q:::::::QQ::::::::Q \n" +
				" QQ::::::::::::::Q  \n" +
				"   QQ:::::::::::Q   \n" +
				"     QQQQQQQQ::::QQ \n" +
				"             Q:::::Q\n" +
				"              QQQQQQ\n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    "),
		R("                    \n" +
				"                    \n" +
				"RRRRRRRRRRRRRRRRR   \n" +
				"R::::::::::::::::R  \n" +
				"R::::::RRRRRR:::::R \n" +
				"RR:::::R     R:::::R\n" +
				"  R::::R     R:::::R\n" +
				"  R::::R     R:::::R\n" +
				"  R::::RRRRRR:::::R \n" +
				"  R:::::::::::::RR  \n" +
				"  R::::RRRRRR:::::R \n" +
				"  R::::R     R:::::R\n" +
				"  R::::R     R:::::R\n" +
				"  R::::R     R:::::R\n" +
				"RR:::::R     R:::::R\n" +
				"R::::::R     R:::::R\n" +
				"R::::::R     R:::::R\n" +
				"RRRRRRRR     RRRRRRR\n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    \n" +
				"                    "),
		S("                   \n" +
				"                   \n" +
				"   SSSSSSSSSSSSSSS \n" +
				" SS:::::::::::::::S\n" +
				"S:::::SSSSSS::::::S\n" +
				"S:::::S     SSSSSSS\n" +
				"S:::::S            \n" +
				"S:::::S            \n" +
				" S::::SSSS         \n" +
				"  SS::::::SSSSS    \n" +
				"    SSS::::::::SS  \n" +
				"       SSSSSS::::S \n" +
				"            S:::::S\n" +
				"            S:::::S\n" +
				"SSSSSSS     S:::::S\n" +
				"S::::::SSSSSS:::::S\n" +
				"S:::::::::::::::SS \n" +
				" SSSSSSSSSSSSSSS   \n" +
				"                   \n" +
				"                   \n" +
				"                   \n" +
				"                   \n" +
				"                   \n" +
				"                   \n" +
				"                   "),
		T("                       \n" +
				"                       \n" +
				"TTTTTTTTTTTTTTTTTTTTTTT\n" +
				"T:::::::::::::::::::::T\n" +
				"T:::::::::::::::::::::T\n" +
				"T:::::TT:::::::TT:::::T\n" +
				"TTTTTT  T:::::T  TTTTTT\n" +
				"        T:::::T        \n" +
				"        T:::::T        \n" +
				"        T:::::T        \n" +
				"        T:::::T        \n" +
				"        T:::::T        \n" +
				"        T:::::T        \n" +
				"        T:::::T        \n" +
				"      TT:::::::TT      \n" +
				"      T:::::::::T      \n" +
				"      T:::::::::T      \n" +
				"      TTTTTTTTTTT      \n" +
				"                       \n" +
				"                       \n" +
				"                       \n" +
				"                       \n" +
				"                       \n" +
				"                       \n" +
				"                       "),
		U("                     \n" +
				"                     \n" +
				"UUUUUUUU     UUUUUUUU\n" +
				"U::::::U     U::::::U\n" +
				"U::::::U     U::::::U\n" +
				"UU:::::U     U:::::UU\n" +
				" U:::::U     U:::::U \n" +
				" U:::::D     D:::::U \n" +
				" U:::::D     D:::::U \n" +
				" U:::::D     D:::::U \n" +
				" U:::::D     D:::::U \n" +
				" U:::::D     D:::::U \n" +
				" U:::::D     D:::::U \n" +
				" U::::::U   U::::::U \n" +
				" U:::::::UUU:::::::U \n" +
				"  UU:::::::::::::UU  \n" +
				"    UU:::::::::UU    \n" +
				"      UUUUUUUUU      \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     "),
		V("                           \n" +
				"                           \n" +
				"VVVVVVVV           VVVVVVVV\n" +
				"V::::::V           V::::::V\n" +
				"V::::::V           V::::::V\n" +
				"V::::::V           V::::::V\n" +
				" V:::::V           V:::::V \n" +
				"  V:::::V         V:::::V  \n" +
				"   V:::::V       V:::::V   \n" +
				"    V:::::V     V:::::V    \n" +
				"     V:::::V   V:::::V     \n" +
				"      V:::::V V:::::V      \n" +
				"       V:::::V:::::V       \n" +
				"        V:::::::::V        \n" +
				"         V:::::::V         \n" +
				"          V:::::V          \n" +
				"           V:::V           \n" +
				"            VVV            \n" +
				"                           \n" +
				"                           \n" +
				"                           \n" +
				"                           \n" +
				"                           \n" +
				"                           \n" +
				"                           "),
		W("                                           \n" +
				"                                           \n" +
				"WWWWWWWW                           WWWWWWWW\n" +
				"W::::::W                           W::::::W\n" +
				"W::::::W                           W::::::W\n" +
				"W::::::W                           W::::::W\n" +
				" W:::::W           WWWWW           W:::::W \n" +
				"  W:::::W         W:::::W         W:::::W  \n" +
				"   W:::::W       W:::::::W       W:::::W   \n" +
				"    W:::::W     W:::::::::W     W:::::W    \n" +
				"     W:::::W   W:::::W:::::W   W:::::W     \n" +
				"      W:::::W W:::::W W:::::W W:::::W      \n" +
				"       W:::::W:::::W   W:::::W:::::W       \n" +
				"        W:::::::::W     W:::::::::W        \n" +
				"         W:::::::W       W:::::::W         \n" +
				"          W:::::W         W:::::W          \n" +
				"           W:::W           W:::W           \n" +
				"            WWW             WWW            \n" +
				"                                           \n" +
				"                                           \n" +
				"                                           \n" +
				"                                           \n" +
				"                                           \n" +
				"                                           \n" +
				"                                           "),
		X("                     \n" +
				"                     \n" +
				"XXXXXXX       XXXXXXX\n" +
				"X:::::X       X:::::X\n" +
				"X:::::X       X:::::X\n" +
				"X::::::X     X::::::X\n" +
				"XXX:::::X   X:::::XXX\n" +
				"   X:::::X X:::::X   \n" +
				"    X:::::X:::::X    \n" +
				"     X:::::::::X     \n" +
				"     X:::::::::X     \n" +
				"    X:::::X:::::X    \n" +
				"   X:::::X X:::::X   \n" +
				"XXX:::::X   X:::::XXX\n" +
				"X::::::X     X::::::X\n" +
				"X:::::X       X:::::X\n" +
				"X:::::X       X:::::X\n" +
				"XXXXXXX       XXXXXXX\n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     "),
		Y("                     \n" +
				"                     \n" +
				"YYYYYYY       YYYYYYY\n" +
				"Y:::::Y       Y:::::Y\n" +
				"Y:::::Y       Y:::::Y\n" +
				"Y::::::Y     Y::::::Y\n" +
				"YYY:::::Y   Y:::::YYY\n" +
				"   Y:::::Y Y:::::Y   \n" +
				"    Y:::::Y:::::Y    \n" +
				"     Y:::::::::Y     \n" +
				"      Y:::::::Y      \n" +
				"       Y:::::Y       \n" +
				"       Y:::::Y       \n" +
				"       Y:::::Y       \n" +
				"       Y:::::Y       \n" +
				"    YYYY:::::YYYY    \n" +
				"    Y:::::::::::Y    \n" +
				"    YYYYYYYYYYYYY    \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     \n" +
				"                     "),
		Z("                   \n" +
				"                   \n" +
				"ZZZZZZZZZZZZZZZZZZZ\n" +
				"Z:::::::::::::::::Z\n" +
				"Z:::::::::::::::::Z\n" +
				"Z:::ZZZZZZZZ:::::Z \n" +
				"ZZZZZ     Z:::::Z  \n" +
				"        Z:::::Z    \n" +
				"       Z:::::Z     \n" +
				"      Z:::::Z      \n" +
				"     Z:::::Z       \n" +
				"    Z:::::Z        \n" +
				"   Z:::::Z         \n" +
				"ZZZ:::::Z     ZZZZZ\n" +
				"Z::::::ZZZZZZZZ:::Z\n" +
				"Z:::::::::::::::::Z\n" +
				"Z:::::::::::::::::Z\n" +
				"ZZZZZZZZZZZZZZZZZZZ\n" +
				"                   \n" +
				"                   \n" +
				"                   \n" +
				"                   \n" +
				"                   \n" +
				"                   \n" +
				"                   "),
		SPACE("     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     "),
		QUESTION("      ???????     \n" +
				"    ??:::::::??   \n" +
				"  ??:::::::::::?  \n" +
				" ?:::::????:::::? \n" +
				" ?::::?    ?::::? \n" +
				" ?::::?     ?::::?\n" +
				" ??????     ?::::?\n" +
				"           ?::::? \n" +
				"          ?::::?  \n" +
				"         ?::::?   \n" +
				"        ?::::?    \n" +
				"       ?::::?     \n" +
				"       ?::::?     \n" +
				"       ??::??     \n" +
				"        ????      \n" +
				"                  \n" +
				"        ???       \n" +
				"       ??:??      \n" +
				"        ???       \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  \n" +
				"                  "),
		EXCLAMATIONMARK("     \n" +
				"     \n" +
				" !!! \n" +
				"!!:!!\n" +
				"!:::!\n" +
				"!:::!\n" +
				"!:::!\n" +
				"!:::!\n" +
				"!:::!\n" +
				"!:::!\n" +
				"!:::!\n" +
				"!:::!\n" +
				"!!:!!\n" +
				" !!! \n" +
				"     \n" +
				" !!! \n" +
				"!!:!!\n" +
				" !!! \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     \n" +
				"     "),
		POINT("        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				" ...... \n" +
				" .::::. \n" +
				" ...... \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        "),
		COMMA("       \n" +
				"       \n" +
				"       \n" +
				"       \n" +
				"       \n" +
				"       \n" +
				"       \n" +
				"       \n" +
				"       \n" +
				"       \n" +
				"       \n" +
				"       \n" +
				"       \n" +
				"       \n" +
				" ,,,,,,\n" +
				" ,::::,\n" +
				" ,::::,\n" +
				" ,:::,,\n" +
				",:::,  \n" +
				",,,,   \n" +
				"       \n" +
				"       \n" +
				"       \n" +
				"       \n" +
				"       "),
		DOUBLEPOINT("        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				" :::::: \n" +
				" :::::: \n" +
				" :::::: \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				" :::::: \n" +
				" :::::: \n" +
				" :::::: \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        \n" +
				"        "),
		BRACKETROUNDLEFT("              \n" +
				"              \n" +
				"       (((((( \n" +
				"     ((::::::(\n" +
				"   ((:::::::( \n" +
				"  (:::::::((  \n" +
				"  (::::::(    \n" +
				"  (:::::(     \n" +
				"  (:::::(     \n" +
				"  (:::::(     \n" +
				"  (:::::(     \n" +
				"  (:::::(     \n" +
				"  (:::::(     \n" +
				"  (::::::(    \n" +
				"  (:::::::((  \n" +
				"   ((:::::::( \n" +
				"     ((::::::(\n" +
				"       (((((( \n" +
				"              \n" +
				"              \n" +
				"              \n" +
				"              \n" +
				"              \n" +
				"              \n" +
				"              "),
		BRACKETROUNDRIGHT("            \n" +
				"            \n" +
				" ))))))     \n" +
				")::::::))   \n" +
				" ):::::::)) \n" +
				"  )):::::::)\n" +
				"    )::::::)\n" +
				"     ):::::)\n" +
				"     ):::::)\n" +
				"     ):::::)\n" +
				"     ):::::)\n" +
				"     ):::::)\n" +
				"     ):::::)\n" +
				"    )::::::)\n" +
				"  )):::::::)\n" +
				" ):::::::)) \n" +
				")::::::)    \n" +
				" ))))))     \n" +
				"            \n" +
				"            \n" +
				"            \n" +
				"            \n" +
				"            \n" +
				"            \n" +
				"            "),
		BRACKETCODINGLEFT("           \n" +
				"      {{{{{\n" +
				"     {::::{\n" +
				"    {:::::{\n" +
				"    {::::{{\n" +
				"   {::::{  \n" +
				"   {::::{  \n" +
				"  {:::::{  \n" +
				" {:::::{   \n" +
				"{:::::{    \n" +
				" {:::::{   \n" +
				"  {:::::{  \n" +
				"   {::::{  \n" +
				"   {::::{  \n" +
				"   {:::::{{\n" +
				"    {:::::{\n" +
				"     {::::{\n" +
				"      {{{{{\n" +
				"           \n" +
				"           \n" +
				"           \n" +
				"           \n" +
				"           \n" +
				"           \n" +
				"           "),
		BRACKETCODINGRIGHT("           \n" +
				"}}}}}      \n" +
				"}::::}     \n" +
				"}:::::}    \n" +
				"}}::::}    \n" +
				"  }::::}   \n" +
				"  }::::}   \n" +
				"  }:::::}  \n" +
				"   }:::::} \n" +
				"    }:::::}\n" +
				"   }:::::} \n" +
				"  }:::::}  \n" +
				"  }::::}   \n" +
				"  }::::}   \n" +
				"}}:::::}   \n" +
				"}:::::}    \n" +
				"}::::}     \n" +
				"}}}}}      \n" +
				"           \n" +
				"           \n" +
				"           \n" +
				"           \n" +
				"           \n" +
				"           \n" +
				"           ");

		@Getter
		private String asciiArt;

		AsciiLetters(String asciiArt) {
			this.asciiArt = asciiArt;
		}
	}
}