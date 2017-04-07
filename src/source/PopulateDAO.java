/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import dao.DealDAO;
import domain.Deal;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Charlie Hard
 */
public class PopulateDAO {

    DealDAO dealDAO = new DealDAO();

    public void getDeals() throws IOException {
        Document doc = Jsoup.connect("https://www.cheapies.nz/").get();
        Elements dealTitles = doc.select("div.n-right");
        for (Element e : dealTitles) {
            Deal d = new Deal();
            if (e.select("h2.title").text().substring(0, 7).equals("expired")) {
                d.setExpired(true);
            } else {
                d.setExpired(false);
            }
            d.setTitle(e.select("h2.title").attr("data-title"));
            d.setDescription(e.select("div.content p").text());
            d.setCoupon(e.select("div.couponcode").text());
            d.setCheapiesURL(e.select("h2 a").attr("abs:href"));
            d.setDealURL(e.select("a").attr("abs:href"));
            dealDAO.addDeal(d);
        }
        // Removes 'Hot Discussions' from list of deals
        dealDAO.removeDeal(dealDAO.getCurrentDeals().size() - 1);
        
        
    }

}
