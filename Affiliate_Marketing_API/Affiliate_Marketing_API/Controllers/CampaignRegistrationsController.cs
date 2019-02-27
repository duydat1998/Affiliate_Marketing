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
    public class CampaignRegistrationsController : ApiController
    {
        private AffiliateMarketingDBEntities db = new AffiliateMarketingDBEntities();

        // GET: api/CampaignRegistrations
        public IQueryable<CampaignRegistration> GetCampaignRegistrations()
        {
            return db.CampaignRegistrations;
        }

        // GET: api/CampaignRegistrations/5
        [ResponseType(typeof(CampaignRegistration))]
        public IHttpActionResult GetCampaignRegistration(string id)
        {
            CampaignRegistration campaignRegistration = db.CampaignRegistrations.Find(id);
            if (campaignRegistration == null)
            {
                return NotFound();
            }

            return Ok(campaignRegistration);
        }

        // PUT: api/CampaignRegistrations/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutCampaignRegistration(string id, CampaignRegistration campaignRegistration)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != campaignRegistration.campaignID)
            {
                return BadRequest();
            }

            db.Entry(campaignRegistration).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CampaignRegistrationExists(id))
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

        // POST: api/CampaignRegistrations
        [ResponseType(typeof(CampaignRegistration))]
        public IHttpActionResult PostCampaignRegistration(CampaignRegistration campaignRegistration)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.CampaignRegistrations.Add(campaignRegistration);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (CampaignRegistrationExists(campaignRegistration.campaignID))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = campaignRegistration.campaignID }, campaignRegistration);
        }

        // DELETE: api/CampaignRegistrations/5
        [ResponseType(typeof(CampaignRegistration))]
        public IHttpActionResult DeleteCampaignRegistration(string id)
        {
            CampaignRegistration campaignRegistration = db.CampaignRegistrations.Find(id);
            if (campaignRegistration == null)
            {
                return NotFound();
            }

            db.CampaignRegistrations.Remove(campaignRegistration);
            db.SaveChanges();

            return Ok(campaignRegistration);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool CampaignRegistrationExists(string id)
        {
            return db.CampaignRegistrations.Count(e => e.campaignID == id) > 0;
        }
    }
}