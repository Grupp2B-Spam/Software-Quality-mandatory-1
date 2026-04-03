package com.example.demo.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.List;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.FakeInfo;

/* Address rules

    Street. A random assortment of alphabetic characters
    Number. A number from 1 to 999 optionally followed by an uppercase letter (e.g., 43B)
    Floor. Either “st” or a number from 1 to 99
    Door. “th”, “mf”, “tv”, a number from 1 to 50, or a lowercase letter optionally followed by a dash, then followed by one to three numeric digits (e.g., c3, d-14)
    Postal code and town. Randomly extracted from the provided database addresses.sql
    More information at https://danmarksadresser.dk/regler-og-vejledning/adresser
*/

@SpringBootTest
class FakeInfoAddressTest {

    List<String> postalCodes = List.of(
        "1301","2000","2100","2200","2300","2400","2450","2500","2600","2605","2610","2625","2630","2635","2640","2650","2660","2665","2670","2680","2690","2700","2720","2730","2740","2750","2760","2765","2770","2791","2800","2820","2830","2840","2850","2860","2880","2900","2920","2930","2942","2950","2960","2970","2980","2990","3000","3050","3060","3070","3080","3100","3120","3140","3150","3200","3210","3220","3230","3250","3300","3310","3320","3330","3360","3370","3390","3400","3460","3480","3490","3500","3520","3540","3550","3600","3630","3650","3660","3670","3700","3720","3730","3740","3751","3760","3770","3782","3790","4000","4040","4050","4060","4070","4100","4130","4140","4160","4171","4173","4174","4180","4190","4200","4220","4230","4241","4242","4243","4250","4261","4262","4270","4281","4291","4293","4295","4296","4300","4320","4330","4340","4350","4360","4370","4390","4400","4420","4440","4450","4460","4470","4480","4490","4500","4520","4532","4534","4540","4550","4560","4571","4572","4573","4581","4583","4591","4592","4593","4600","4621","4622","4623","4632","4640","4652","4653","4654","4660","4671","4672","4673","4681","4682","4683","4684","4690","4700","4703","4720","4733","4735","4736","4750","4760","4771","4772","4773","4780","4791","4792","4793","4800","4840","4850","4862","4863","4871","4872","4873","4874","4880","4891","4892","4894","4895","4900","4912","4913","4920","4930","4941","4943","4944","4951","4952","4953","4960","4970","4983","4990","5000","5200","5210","5220","5230","5240","5250","5260","5270","5290","5300","5320","5330","5350","5370","5380","5390","5400","5450","5462","5463","5464","5466","5471","5474","5485","5491","5492","5500","5540","5550","5560","5580","5591","5592","5600","5610","5620","5631","5642","5672","5683","5690","5700","5750","5762","5771","5772","5792","5800","5853","5854","5856","5863","5871","5874","5881","5882","5883","5884","5892","5900","5932","5935","5953","5960","5970","5985","6000","6040","6051","6052","6064","6070","6091","6092","6093","6094","6100","6200","6230","6240","6261","6270","6280","6300","6310","6320","6330","6340","6360","6372","6392","6400","6430","6440","6470","6500","6510","6520","6534","6535","6541","6560","6580","6600","6621","6622","6623","6630","6640","6650","6660","6670","6682","6683","6690","6700","6701","6705","6710","6715","6720","6731","6740","6752","6753","6760","6771","6780","6792","6800","6818","6823","6830","6840","6851","6852","6853","6854","6855","6857","6862","6870","6880","6893","6900","6920","6933","6940","6950","6960","6971","6973","6980","6990","7000","7080","7100","7120","7130","7140","7150","7160","7171","7173","7182","7183","7184","7190","7200","7250","7260","7270","7280","7300","7321","7323","7330","7361","7362","7400","7430","7441","7442","7451","7470","7480","7490","7500","7540","7550","7560","7570","7600","7620","7650","7660","7673","7680","7700","7730","7741","7742","7752","7755","7760","7770","7790","7800","7830","7840","7850","7860","7870","7884","7900","7950","7960","7970","7980","7990","8000","8100","8200","8210","8220","8230","8240","8250","8260","8270","8300","8305","8310","8320","8330","8340","8350","8355","8361","8362","8363","8370","8380","8381","8382","8400","8410","8420","8444","8450","8462","8464","8471","8472","8500","8520","8530","8541","8543","8544","8550","8560","8570","8581","8585","8586","8592","8600","8620","8632","8641","8643","8653","8654","8660","8670","8680","8700","8721","8722","8723","8732","8740","8751","8752","8762","8763","8765","8766","8781","8783","8800","8830","8831","8832","8840","8850","8860","8870","8881","8882","8883","8900","8920","8930","8940","8950","8960","8961","8963","8970","8981","8983","8990","9000","9200","9210","9220","9230","9240","9260","9270","9280","9293","9300","9310","9320","9330","9340","9352","9362","9370","9380","9381","9382","9400","9430","9440","9460","9480","9490","9492","9493","9500","9510","9520","9530","9541","9550","9560","9574","9575","9600","9610","9620","9631","9632","9640","9670","9681","9690","9700","9740","9750","9760","9800","9830","9850","9870","9881","9900","9940","9970","9981","9982","9990"
    );

    List<String> towns = List.of(
        "København K","Frederiksberg","København Ø","København N","København S","København NV","København SV","Valby","Glostrup","Brøndby","Rødovre","Vallensbæk","Taastrup","Ishøj","Hedehusene","Hvidovre","Brøndby Strand","Vallensbæk Strand","Greve","Solrød Strand","Karlslunde","Brønshøj","Vanløse","Herlev","Skovlunde","Ballerup","Måløv","Smørum","Kastrup","Dragør","Kongens Lyngby","Gentofte","Virum","Holte","Nærum","Søborg","Bagsværd","Hellerup","Charlottenlund","Klampenborg","Skodsborg","Vedbæk","Rungsted Kyst","Hørsholm","Kokkedal","Nivå","Helsingør","Humlebæk","Espergærde","Snekkersten","Tikøb","Hornbæk","Dronningmølle","Ålsgårde","Hellebæk","Helsinge","Vejby","Tisvildeleje","Græsted","Gilleleje","Frederiksværk","Ølsted","Skævinge","Gørløse","Liseleje","Melby","Hundested","Hillerød","Birkerød","Fredensborg","Kvistgård","Værløse","Farum","Lynge","Slangerup","Frederikssund","Jægerspris","Ølstykke","Stenløse","Veksø Sjælland","Rønne","Aakirkeby","Nexø","Svaneke","Østermarie","Gudhjem","Allinge","Klemensker","Hasle","Roskilde","Jyllinge","Skibby","Kirke Såby","Kirke Hyllinge","Ringsted","Viby Sjælland","Borup","Herlufmagle","Glumsø","Fjenneslev","Jystrup","Sorø","Munke Bjergby","Slagelse","Korsør","Skælskør","Vemmelev","Boeslunde","Rude","Fuglebjerg","Dalmose","Sandved","Høng","Gørlev","Ruds Vedby","Dianalund","Stenlille","Nyrup","Holbæk","Lejre","Hvalsø","Tølløse","Ugerløse","Kirke Eskilstrup","Store Merløse","Vipperød","Kalundborg","Regstrup","Mørkøv","Jyderup","Snertinge","Svebølle","Store Fuglede","Jerslev","Nykøbing Sj","Svinninge","Gislinge","Hørve","Fårevejle","Asnæs","Vig","Grevinge","Nørre Asmindrup","Højby","Rørvig","Sjællands Odde","Føllenslev","Sejerø","Eskebjerg","Køge","Gadstrup","Havdrup","Lille Skensved","Bjæverskov","Fakse","Hårlev","Karise","Fakse Ladeplads","Store Heddinge","Strøby","Klippinge","Rødvig Stevns","Herfølge","Tureby","Rønnede","Holme-Olstrup","Haslev","Næstved","Næstved Postbutik","Præstø","Tappernøje","Mern","Karrebæksminde","Lundby","Vordingborg","Kalvehave","Langebæk","Stensved","Stege","Borre","Askeby","Bogø By","Nykøbing Falster","Nørre Alslev","Stubbekøbing","Guldborg","Eskilstrup","Horbelev","Idestrup","Væggerløse","Gedser","Nysted","Toreby Lolland","Kettinge","Øster Ulslev","Errindlev","Nakskov","Harpelunde","Horslunde","Søllested","Maribo","Bandholm","Torrig Lolland","Fejø","Nørreballe","Stokkemarke","Vesterborg","Holeby","Rødby","Dannemare","Sakskøbing","Odense C","Odense V","Odense NV","Odense SØ","Odense M","Odense NØ","Odense SV","Odense S","Odense N","Marslev","Kerteminde","Agedrup","Munkebo","Rynkeby","Mesinge","Dalby","Martofte","Bogense","Otterup","Morud","Harndrup","Brenderup Fyn","Asperup","Søndersø","Veflinge","Skamby","Blommenslyst","Vissenbjerg","Middelfart","Ullerslev","Langeskov","Aarup","Nørre Aaby","Gelsted","Ejby","Faaborg","Assens","Glamsbjerg","Ebberup","Millinge","Broby","Haarby","Tommerup","Svendborg","Ringe","Vester Skerninge","Stenstrup","Kværndrup","Årslev","Nyborg","Ørbæk","Gislev","Ryslinge","Ferritslev Fyn","Frørup","Hesselager","Skårup Fyn","Vejstrup","Oure","Gudme","Gudbjerg Sydfyn","Rudkøbing","Humble","Bagenkop","Tranekær","Marstal","Ærøskøbing","Søby Ærø","Kolding","Egtved","Almind","Viuf","Jordrup","Christiansfeld","Bjert","Sønder Stenderup","Sjølund","Hejls","Haderslev","Aabenraa","Rødekro","Løgumkloster","Bredebro","Tønder","Højer","Gråsten","Broager","Egernsund","Padborg","Kruså","Tinglev","Bylderup-Bov","Bolderslev","Sønderborg","Nordborg","Augustenborg","Sydals","Vojens","Gram","Toftlund","Agerskov","Branderup Jylland","Bevtoft","Sommersted","Vamdrup","Vejen","Gesten","Bække","Vorbasse","Rødding","Lunderskov","Brørup","Lintrup","Holsted","Hovborg","Føvling","Gørding","Esbjerg","Esbjerg","Esbjerg Ø","Esbjerg V","Esbjerg N","Fanø","Tjæreborg","Bramming","Glejbjerg","Agerbæk","Ribe","Gredstedbro","Skærbæk","Rømø","Varde","Årre","Ansager","Nørre Nebel","Oksbøl","Janderup Vestjylland","Billum","Vejers Strand","Henne","Outrup","Blåvand","Tistrup","Ølgod","Tarm","Hemmet","Skjern","Videbæk","Kibæk","Lem St.","Ringkøbing","Hvide Sande","Spjald","Ørnhøj","Tim","Ulfborg","Fredericia","Børkop","Vejle","Vejle Øst","Juelsminde","Stouby","Barrit","Tørring","Uldum","Vonge","Bredsten","Randbøl","Vandel","Billund","Grindsted","Hejnsvig","Sønder Omme","Stakroge","Sønder Felding","Jelling","Gadbjerg","Give","Brande","Ejstrupholm","Hampen","Herning","Ikast","Bording","Engesvang","Sunds","Karup Jylland","Vildbjerg","Aulum","Holstebro","Haderup","Sørvad","Hjerm","Vemb","Struer","Lemvig","Bøvlingbjerg","Bækmarksbro","Harboøre","Thyborøn","Thisted","Hanstholm","Frøstrup","Vesløs","Snedsted","Bedsted Thy","Hurup Thy","Vestervig","Thyholm","Skive","Vinderup","Højslev","Stoholm, Jylland","Spøttrup","Roslev","Fur","Nykøbing Mors","Erslev","Karby","Redsted Mors","Vils","Øster Assels","Aarhus C","Aarhus Rådhus","Aarhus N","Aarhus V","Brabrand","Åbyhøj","Risskov","Egå","Viby Jylland","Højbjerg","Odder","Samsø","Tranbjerg Jylland","Mårslet","Beder","Malling","Hundslund","Solbjerg","Hasselager","Hørning","Stilling","Hadsten","Trige","Tilst","Hinnerup","Ebeltoft","Rønde","Knebel","Balle","Hammel","Harlev","Galten","Sabro","Sporup","Grenaa","Lystrup","Hjortshøj","Skødstrup","Hornslet","Mørke","Ryomgård","Kolind","Trustrup","Nimtofte","Glesborg","Ørum Djurs","Anholt","Silkeborg","Kjellerup","Lemming","Sorring","Ans","Them","Bryrup","Skanderborg","Låsby","Ry","Horsens","Daugård","Hedensted","Løsning","Hovedgård","Brædstrup","Gedved","Østbirk","Flemming","Rask Mølle","Klovborg","Nørre Snede","Stenderup","Hornsyld","Viborg","Tjele","Løgstrup","Skals","Rødkærsbro","Bjerringbro","Ulstrup","Langå","Thorsø","Fårvang","Gjern","Randers C","Randers NV","Randers NØ","Randers SV","Ørsted","Randers SØ","Allingåbro","Auning","Havndal","Spentrup","Gjerlev Jylland","Fårup","Aalborg","Aalborg SV","Aalborg SØ","Aalborg Øst","Svenstrup Jylland","Nibe","Gistrup","Klarup","Storvorde","Kongerslev","Sæby","Vodskov","Hjallerup","Dronninglund","Asaa","Dybvad","Gandrup","Hals","Vestbjerg","Sulsted","Tylstrup","Nørresundby","Vadum","Aabybro","Brovst","Løkken","Pandrup","Blokhus","Saltum","Hobro","Arden","Skørping","Støvring","Suldrup","Mariager","Hadsund","Bælum","Terndrup","Aars","Nørager","Aalestrup","Gedsted","Møldrup","Farsø","Løgstør","Ranum","Fjerritslev","Brønderslev","Jerslev J","Østervrå","Vrå","Hjørring","Tårs","Hirtshals","Sindal","Bindslev","Frederikshavn","Læsø","Strandby","Jerup","Aalbæk","Skagen"
    );

    @Test
        // Verifies that the method returns a non-null result object.
    void getAddress_shouldReturnNonNullData() {
        FakeInfo person = new FakeInfo();

        Map<String,Map<String, Object>> redundantWrapperMapAddress = person.getAddress();

        assertNotNull(redundantWrapperMapAddress);
    }

    @Test
        // test if true address data map is not null
        //Since someone unnecessarily makes a nested mapping to return the address.
        //Which makes me ask why?
        //The outer mapping should be removed.
    void getAddress_nestedData_shouldReturnNonNullData() {
        FakeInfo person = new FakeInfo();

        Map<String,Object> address = person.getAddress().get("address");

        assertNotNull(address);
    }

    @Test
        // Verifies that parameters of address are not null.
    void getAddress_params_shouldReturnNonNull() {
        FakeInfo person = new FakeInfo();

        Map<String,Object> address = person.getAddress().get("address");

        assertNotNull(address.get("number"));
        assertNotNull(address.get("door"));
        assertNotNull(address.get("town_name"));
        assertNotNull(address.get("street"));
        assertNotNull(address.get("floor"));
        assertNotNull(address.get("postal_code"));
    }


    //number
    @RepeatedTest(50)
        //Rules:
        /*
            Number. A number from 1 to 999 optionally followed by an uppercase letter (e.g., 43B)
        */
    void getAddress_number_shouldMatchTheRules() {
        FakeInfo person = new FakeInfo();

        Map<String,Object> address = person.getAddress().get("address");
        String number = address.get("number").toString();

        assertTrue(number.matches("^\\d{1,3}$|^\\d{1,3}[A-Z]$"));
    }

    @RepeatedTest(50)
        //Rules:
        /*
            Bogstaverne I, J, O og Q må dog ikke benyttes.
        */
    void getAddress_number_shouldNotContainSpecificLetters() {
        FakeInfo person = new FakeInfo();

        Map<String,Object> address = person.getAddress().get("address");
        String number = address.get("number").toString();

        assertTrue(!number.contains("I"));
        assertTrue(!number.contains("J"));
        assertTrue(!number.contains("O"));
        assertTrue(!number.contains("Q"));
    }

    //floor
    @RepeatedTest(50)
        //Rules:
        /*
            Floor. Either “st” or a number from 1 to 99
        */
    void getAddress_floor_shouldMatchTheRules() {
        FakeInfo person = new FakeInfo();

        Map<String,Object> address = person.getAddress().get("address");
        String floor = address.get("floor").toString();

        assertTrue(floor.matches("^\\d{1,2}$|^\\w[st]$"));
    }

    //door
    @RepeatedTest(50)
        //Rules:
        /*
            Door. “th”, “mf”, “tv”, a number from 1 to 50, or a lowercase letter optionally followed by a dash, then followed by one to three numeric digits (e.g., c3, d-14)
        */
    void getAddress_door_shouldMatchTheRules() {
        FakeInfo person = new FakeInfo();

        Map<String,Object> address = person.getAddress().get("address");
        String door = address.get("door").toString();

        assertTrue(door.matches("^\\d{1,2}$|^\\w[th]$|^\\w[mf]$|^\\w[tv]$|^[a-zæøå][-]\\d{1,3}$|^[a-zæøå]\\d{1,3}$"), "Door name doesn't follow the rules: " + door);
    }

    //town
    @Test
    void getAddress_town_shouldMatchDatabaseScript() {
        FakeInfo person = new FakeInfo();

        Map<String,Object> address = person.getAddress().get("address");
        String townName = address.get("town_name").toString();

        
        assertTrue(towns.contains(townName));
    }

     //postal code
    @Test
    void getAddress_postal_code_shouldMatchDatabaseScript() {
        FakeInfo person = new FakeInfo();

        Map<String,Object> address = person.getAddress().get("address");
        String postalCode = address.get("postal_code").toString();

        
        assertTrue(postalCodes.contains(postalCode));
    }

    @Test
    void getAddress_postal_code_shouldBeFourDigitsLongAllways() {
        FakeInfo person = new FakeInfo();

        Map<String,Object> address = person.getAddress().get("address");
        String postalCode = address.get("postal_code").toString();
        
        assertTrue(postalCode.matches("^\\d{4}$"));
    }
}