package io.getquill.norm.capture

import scala.language.reflectiveCalls

import io.getquill._

class DealiasSpec extends Spec {

  "ensures that each entity is referenced by the same alias" - {
    "flatMap" in {
      val q = quote {
        qr1.filter(a => a.s == "s").flatMap(b => qr2)
      }
      val n = quote {
        qr1.filter(a => a.s == "s").flatMap(a => qr2)
      }
      Dealias(q.ast) mustEqual n.ast
    }
    "map" in {
      val q = quote {
        qr1.filter(a => a.s == "s").map(b => b.s)
      }
      val n = quote {
        qr1.filter(a => a.s == "s").map(a => a.s)
      }
      Dealias(q.ast) mustEqual n.ast
    }
    "filter" in {
      val q = quote {
        qr1.filter(a => a.s == "s").filter(b => b.s != "l")
      }
      val n = quote {
        qr1.filter(a => a.s == "s").filter(a => a.s != "l")
      }
      Dealias(q.ast) mustEqual n.ast
    }
    "sortBy" in {
      val q = quote {
        qr1.filter(a => a.s == "s").sortBy(b => b.s)
      }
      val n = quote {
        qr1.filter(a => a.s == "s").sortBy(a => a.s)
      }
      Dealias(q.ast) mustEqual n.ast
    }
    "groupBy" in {
      val q = quote {
        qr1.filter(a => a.s == "s").groupBy(b => b.s)
      }
      val n = quote {
        qr1.filter(a => a.s == "s").groupBy(a => a.s)
      }
      Dealias(q.ast) mustEqual n.ast
    }
    "aggregation" in {
      val q = quote {
        qr1.map(a => a.i).max
      }
      Dealias(q.ast) mustEqual q.ast
    }
    "reverse" in {
      val q = quote {
        qr1.sortBy(a => a.s).reverse.map(b => b.s)
      }
      val n = quote {
        qr1.sortBy(a => a.s).reverse.map(a => a.s)
      }
      Dealias(q.ast) mustEqual n.ast
    }
    "take" in {
      val q = quote {
        qr1.filter(a => a.s == "s").take(10).map(b => b.s)
      }
      val n = quote {
        qr1.filter(a => a.s == "s").take(10).map(a => a.s)
      }
      Dealias(q.ast) mustEqual n.ast
    }
    "drop" in {
      val q = quote {
        qr1.filter(a => a.s == "s").drop(10).map(b => b.s)
      }
      val n = quote {
        qr1.filter(a => a.s == "s").drop(10).map(a => a.s)
      }
      Dealias(q.ast) mustEqual n.ast
    }
    "union" - {
      "left" in {
        val q = quote {
          qr1.filter(a => a.s == "s").map(b => b.s).union(qr1)
        }
        val n = quote {
          qr1.filter(a => a.s == "s").map(a => a.s).union(qr1)
        }
        Dealias(q.ast) mustEqual n.ast
      }
      "right" in {
        val q = quote {
          qr1.union(qr1.filter(a => a.s == "s").map(b => b.s))
        }
        val n = quote {
          qr1.union(qr1.filter(a => a.s == "s").map(a => a.s))
        }
        Dealias(q.ast) mustEqual n.ast
      }
    }
    "unionAll" - {
      "left" in {
        val q = quote {
          qr1.filter(a => a.s == "s").map(b => b.s).unionAll(qr1)
        }
        val n = quote {
          qr1.filter(a => a.s == "s").map(a => a.s).unionAll(qr1)
        }
        Dealias(q.ast) mustEqual n.ast
      }
      "right" in {
        val q = quote {
          qr1.unionAll(qr1.filter(a => a.s == "s").map(b => b.s))
        }
        val n = quote {
          qr1.unionAll(qr1.filter(a => a.s == "s").map(a => a.s))
        }
        Dealias(q.ast) mustEqual n.ast
      }
    }
    "entity" in {
      Dealias(qr1.ast) mustEqual qr1.ast
    }
  }
}
