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
    public class CampaignsController : ApiController
    {
        private AffiliateMarketingDBEntities db = new AffiliateMarketingDBEntities();

        /// <summary>
        /// get all on-going Campaign
        /// </summary>
        /// <returns></returns>
        // GET: api/Campaigns
        public IQueryable<Campaign> GetCampaigns()
        {
            return db.Campaigns;
        }

        /// <summary>
        /// Get Campaign by CampaignID
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        // GET: api/Campaigns/5
        [ResponseType(typeof(Campaign))]
        public IHttpActionResult GetCampaign(string id)
        {
            Campaign campaign = db.Campaigns.Find(id);
            if (campaign == null)
            {
                return NotFound();
            }

            return Ok(campaign);
        }

        // PUT: api/Campaigns/5
        //[ResponseType(typeof(void))]
        //public IHttpActionResult PutCampaign(string id, Campaign campaign)
        //{
        //    if (!ModelState.IsValid)
        //    {
        //        return BadRequest(ModelState);
        //    }

        //    if (id != campaign.campaignID)
        //    {
        //        return BadRequest();
        //    }

        //    db.Entry(campaign).State = EntityState.Modified;

        //    try
        //    {
        //        db.SaveChanges();
        //    }
        //    catch (DbUpdateConcurrencyException)
        //    {
        //        if (!CampaignExists(id))
        //        {
        //            return NotFound();
        //        }
        //        else
        //        {
        //            throw;
        //        }
        //    }

        //    return StatusCode(HttpStatusCode.NoContent);
        //}

        // POST: api/Campaigns
        //[ResponseType(typeof(Campaign))]
        //public IHttpActionResult PostCampaign(Campaign campaign)
        //{
        //    if (!ModelState.IsValid)
        //    {
        //        return BadRequest(ModelState);
        //    }

        //    db.Campaigns.Add(campaign);

        //    try
        //    {
        //        db.SaveChanges();
        //    }
        //    catch (DbUpdateException)
        //    {
        //        if (CampaignExists(campaign.campaignID))
        //        {
        //            return Conflict();
        //        }
        //        else
        //        {
        //            throw;
        //        }
        //    }

        //    return CreatedAtRoute("DefaultApi", new { id = campaign.campaignID }, campaign);
        //}

        // DELETE: api/Campaigns/5
        //[ResponseType(typeof(Campaign))]
        //public IHttpActionResult DeleteCampaign(string id)
        //{
        //    Campaign campaign = db.Campaigns.Find(id);
        //    if (campaign == null)
        //    {
        //        return NotFound();
        //    }

        //    db.Campaigns.Remove(campaign);
        //    db.SaveChanges();

        //    return Ok(campaign);
        //}

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool CampaignExists(string id)
        {
            return db.Campaigns.Count(e => e.campaignID == id) > 0;
        }
    }
}