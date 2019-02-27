using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using Affiliate_Marketing_API.Models;

namespace Affiliate_Marketing_API.Controllers
{
    public class PromotionCodesController : ApiController
    {
        private AffiliateMarketingDBEntities db = new AffiliateMarketingDBEntities();

        // GET: api/PromotionCodes
        public IQueryable<PromotionCode> GetPromotionCodes()
        {
            return db.PromotionCodes;
        }

        // GET: api/PromotionCodes/5
        [ResponseType(typeof(PromotionCode))]
        public IHttpActionResult GetPromotionCode(string id)
        {
            PromotionCode promotionCode = db.PromotionCodes.Find(id);
            if (promotionCode == null)
            {
                return NotFound();
            }

            return Ok(promotionCode);
        }

        // PUT: api/PromotionCodes/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutPromotionCode(string id, PromotionCode promotionCode)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != promotionCode.promotionCode1)
            {
                return BadRequest();
            }

            db.Entry(promotionCode).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PromotionCodeExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/PromotionCodes
        [ResponseType(typeof(PromotionCode))]
        public IHttpActionResult PostPromotionCode(PromotionCode promotionCode)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.PromotionCodes.Add(promotionCode);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (PromotionCodeExists(promotionCode.promotionCode1))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = promotionCode.promotionCode1 }, promotionCode);
        }

        // DELETE: api/PromotionCodes/5
        [ResponseType(typeof(PromotionCode))]
        public IHttpActionResult DeletePromotionCode(string id)
        {
            PromotionCode promotionCode = db.PromotionCodes.Find(id);
            if (promotionCode == null)
            {
                return NotFound();
            }

            db.PromotionCodes.Remove(promotionCode);
            db.SaveChanges();

            return Ok(promotionCode);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool PromotionCodeExists(string id)
        {
            return db.PromotionCodes.Count(e => e.promotionCode1 == id) > 0;
        }
    }
}