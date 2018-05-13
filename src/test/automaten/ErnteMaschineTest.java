package automaten;

import java.util.ArrayList;
import java.util.List;

import nutzpflanzen.Mais;
import nutzpflanzen.Pflanze;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ErnteMaschineTest
{
   List<Pflanze> maisTestFeldNichtErntbar = new ArrayList();
   List<Pflanze> maisTestFeldErntbar = new ArrayList();
   List<Pflanze> weizenTestFeld = new ArrayList();

   @Before
   public void setData()
   {
      maisTestFeldNichtErntbar.add(new Mais(5.5));
      maisTestFeldNichtErntbar.add(new Mais(5.5));
      maisTestFeldNichtErntbar.add(new Mais(5.5));

      maisTestFeldErntbar.add(new Mais(100.0));
      maisTestFeldErntbar.add(new Mais(100.1));
      maisTestFeldErntbar.add(new Mais(123.25));

      weizenTestFeld.add(new Mais(5.5));
      weizenTestFeld.add(new Mais(5.5));
      weizenTestFeld.add(new Mais(5.5));
   }


   @Test
   public void arbeitenTest()
   {
      new ErnteMaschine().arbeiten(maisTestFeldErntbar);
      assertEquals(maisTestFeldNichtErntbar.size(), 3);

      new ErnteMaschine().arbeiten(maisTestFeldErntbar);
      assertEquals(maisTestFeldErntbar.size(), 0);
   }

   @After
   public void after() throws Exception
   {
      maisTestFeldNichtErntbar = null;
      maisTestFeldErntbar = null;
      weizenTestFeld = null;
      assertNull(maisTestFeldNichtErntbar);
      assertNull(maisTestFeldErntbar);
      assertNull(weizenTestFeld);
   }
}
