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
        /// <summary>
        /// Get all Campaign Registration
        /// </summary>
        /// <returns></returns>
        // GET: api/CampaignRegistrations
        public IQueryable<CampaignRegistration> GetCampaignRegistrations()
        {
            return db.CampaignRegistrations;
        }


        /// <summary>
        /// Get Campaign Registration detail by ID
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
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

        /// <summary>
        /// Update Campaign registration
        /// </summary>
        /// <param name="id"></param>
        /// <param name="campaignRegistration"></param>
        /// <returns></returns>
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

        /// <summary>
        /// Register new Campaign
        /// </summary>
        /// <param name="registrationObject"></param>
        /// <returns></returns>
        // POST: api/CampaignRegistrations
        [ResponseType(typeof(CampaignRegistration))]
        public IHttpActionResult PostCampaignRegistration(CampaignRegistrationObject registrationObject)
        {
            System.DateTime date = DateTime.Now;
            CampaignRegistration campaignRegistration = new CampaignRegistration();
            campaignRegistration.campaignID = registrationObject.campaignID;
            campaignRegistration.promotionCode = registrationObject.promotionCode;
            campaignRegistration.publisherID = registrationObject.publisherID;
            campaignRegistration.registerDate = date;
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

        /// <summary>
        /// Delete Campaign Registration/ Unregister a campaign
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
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

        /// <summary>
        /// Get the Campaign of the registration with promotion Code
        /// </summary>
        /// <param name="promotionCode"></param>
        /// <returns></returns>
        [Route("api/CampaignRegistrations/{promotionCode}/Campaign")]
        [ResponseType(typeof(Campaign))]
        public IHttpActionResult GetCampaignName(string promotionCode)
        {
            IQueryable<CampaignRegistration> campaignRegistrations = db.CampaignRegistrations.Where(c => c.promotionCode.Equals(promotionCode));
            if(campaignRegistrations == null)
            {
                return NotFound();
            }
            else
            {
                CampaignRegistration campaignRegistration = campaignRegistrations.First();
                Campaign campaign = db.Campaigns.Find(campaignRegistration.campaignID);
                if(campaign != null)
                {
                    return Ok(campaign);
                }
            }
            return NotFound();

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