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
    public class AdvertisersController : ApiController
    {
        private AffiliateMarketingDBEntities db = new AffiliateMarketingDBEntities();

        //// GET: api/Advertisers
        //public IQueryable<Advertiser> GetAdvertisers()
        //{
        //    return db.Advertisers;
        //}

        /// <summary>
        /// Get Advertiser detail by AdvertiserID
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        // GET: api/Advertisers/5
        [ResponseType(typeof(Advertiser))]
        public IHttpActionResult GetAdvertiser(string id)
        {
            Advertiser advertiser = db.Advertisers.Find(id);
            if (advertiser == null)
            {
                return NotFound();
            }

            return Ok(advertiser);
        }
        
        /// <summary>
        /// Get Advertiser name by id
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [ResponseType(typeof(string))]
        [Route("api/Advertisers/Name/{id}")]
        public IHttpActionResult GetAdvertiserName(string id)
        {
            Advertiser advertiser = db.Advertisers.Find(id);
            if (advertiser == null)
            {
                return NotFound();
            }

            return Ok(advertiser.advertiserName);
        }
        // PUT: api/Advertisers/5
        //[ResponseType(typeof(void))]
        //public IHttpActionResult PutAdvertiser(string id, Advertiser advertiser)
        //{
        //    if (!ModelState.IsValid)
        //    {
        //        return BadRequest(ModelState);
        //    }

        //    if (id != advertiser.advertiserID)
        //    {
        //        return BadRequest();
        //    }

        //    db.Entry(advertiser).State = EntityState.Modified;

        //    try
        //    {
        //        db.SaveChanges();
        //    }
        //    catch (DbUpdateConcurrencyException)
        //    {
        //        if (!AdvertiserExists(id))
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

        // POST: api/Advertisers
        //[ResponseType(typeof(Advertiser))]
        //public IHttpActionResult PostAdvertiser(Advertiser advertiser)
        //{
        //    if (!ModelState.IsValid)
        //    {
        //        return BadRequest(ModelState);
        //    }

        //    db.Advertisers.Add(advertiser);

        //    try
        //    {
        //        db.SaveChanges();
        //    }
        //    catch (DbUpdateException)
        //    {
        //        if (AdvertiserExists(advertiser.advertiserID))
        //        {
        //            return Conflict();
        //        }
        //        else
        //        {
        //            throw;
        //        }
        //    }

        //    return CreatedAtRoute("DefaultApi", new { id = advertiser.advertiserID }, advertiser);
        //}

        // DELETE: api/Advertisers/5
        //[ResponseType(typeof(Advertiser))]
        //public IHttpActionResult DeleteAdvertiser(string id)
        //{
        //    Advertiser advertiser = db.Advertisers.Find(id);
        //    if (advertiser == null)
        //    {
        //        return NotFound();
        //    }

        //    db.Advertisers.Remove(advertiser);
        //    db.SaveChanges();

        //    return Ok(advertiser);
        //}

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool AdvertiserExists(string id)
        {
            return db.Advertisers.Count(e => e.advertiserID == id) > 0;
        }
    }
}