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
    public class PromotionCodeTrackingsController : ApiController
    {
        private AffiliateMarketingDBEntities db = new AffiliateMarketingDBEntities();

        // GET: api/PromotionCodeTrackings
        public IQueryable<PromotionCodeTracking> GetPromotionCodeTrackings()
        {
            return db.PromotionCodeTrackings;
        }

        // GET: api/PromotionCodeTrackings/5
        [ResponseType(typeof(PromotionCodeTracking))]
        public IHttpActionResult GetPromotionCodeTracking(int id)
        {
            PromotionCodeTracking promotionCodeTracking = db.PromotionCodeTrackings.Find(id);
            if (promotionCodeTracking == null)
            {
                return NotFound();
            }

            return Ok(promotionCodeTracking);
        }

        // PUT: api/PromotionCodeTrackings/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutPromotionCodeTracking(int id, PromotionCodeTracking promotionCodeTracking)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != promotionCodeTracking.trackingId)
            {
                return BadRequest();
            }

            db.Entry(promotionCodeTracking).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PromotionCodeTrackingExists(id))
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

        // POST: api/PromotionCodeTrackings
        [ResponseType(typeof(PromotionCodeTracking))]
        public IHttpActionResult PostPromotionCodeTracking(PromotionCodeTracking promotionCodeTracking)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.PromotionCodeTrackings.Add(promotionCodeTracking);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = promotionCodeTracking.trackingId }, promotionCodeTracking);
        }

        // DELETE: api/PromotionCodeTrackings/5
        [ResponseType(typeof(PromotionCodeTracking))]
        public IHttpActionResult DeletePromotionCodeTracking(int id)
        {
            PromotionCodeTracking promotionCodeTracking = db.PromotionCodeTrackings.Find(id);
            if (promotionCodeTracking == null)
            {
                return NotFound();
            }

            db.PromotionCodeTrackings.Remove(promotionCodeTracking);
            db.SaveChanges();

            return Ok(promotionCodeTracking);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool PromotionCodeTrackingExists(int id)
        {
            return db.PromotionCodeTrackings.Count(e => e.trackingId == id) > 0;
        }
    }
}