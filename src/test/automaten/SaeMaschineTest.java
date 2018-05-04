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

public class SaeMaschineTest
{
   List<Pflanze> maisTestFeld = new ArrayList();
   List<Pflanze> weizenTestFeld = new ArrayList();

   @Before
   public void setData()
   {
      maisTestFeld.add(new Mais(5.5));
      maisTestFeld.add(new Mais(5.5));
      maisTestFeld.add(new Mais(5.5));

      weizenTestFeld.add(new Mais(5.5));
      weizenTestFeld.add(new Mais(5.5));
      weizenTestFeld.add(new Mais(5.5));
   }


   @Test
   public void arbeiten()
   {
      new SaeMaschine().arbeiten(maisTestFeld, "mais");
      assertEquals(maisTestFeld.size(), 50);

      new SaeMaschine().arbeiten(weizenTestFeld, "weizen");
      assertEquals(weizenTestFeld.size(), 50);
   }

   @After
   public void after() throws Exception
   {
      maisTestFeld = null;
      weizenTestFeld = null;
      assertNull(maisTestFeld);
      assertNull(weizenTestFeld);
   }
}
