package vs.fitnessOnline.service;

import java.net.URL;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import vs.fitnessOnline.dto.VijestiOdgovor;

@Service
public class VijestiService {

	@Value("${application.rss.feed.url}")
	private String rssFeedUrl;

	public Set<VijestiOdgovor> vratiNajnovijeVijesti() {
		try {
			URL feedUrl = new URL(rssFeedUrl);
			SyndFeedInput sfi = new SyndFeedInput();
			SyndFeed syndFeed = sfi.build(new XmlReader(feedUrl));

			Set<VijestiOdgovor> vijestiOdgovori = new HashSet<>();

			for (SyndEntry syndEntry : syndFeed.getEntries()) {
				VijestiOdgovor vijestiOdgovor = new VijestiOdgovor();
				vijestiOdgovor.setLink(syndEntry.getLink());
				vijestiOdgovor.setTitle(syndEntry.getTitle());
				vijestiOdgovori.add(vijestiOdgovor);
			}
			return vijestiOdgovori;
		} catch (Exception e) {
			return null;
		}
	}

}
