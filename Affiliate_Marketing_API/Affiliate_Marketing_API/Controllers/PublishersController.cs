﻿using System;
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
    public class PublishersController : ApiController
    {
        private AffiliateMarketingDBEntities db = new AffiliateMarketingDBEntities();

        //// GET: api/Publishers
        //public IQueryable<Publisher> GetPublishers()
        //{
        //    return db.Publishers;
        //}


        /// <summary>
        /// Get Publisher information by publisherID
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        // GET: api/Publishers/5
        [ResponseType(typeof(Publisher))]
        public IHttpActionResult GetPublisher(string id)
        {
            Publisher publisher = db.Publishers.Find(id);
            if (publisher == null)
            {
                return NotFound();
            }

            return Ok(publisher);
        }

        /// <summary>
        /// Login by username and password
        /// </summary>
        /// <param name="login"></param>
        /// <returns></returns>
        //[HttpPost]
        [Route("api/Publishers/Login")]
        [ResponseType(typeof(Publisher))]
        public IHttpActionResult PostPublisherLogin(LoginObject login)
        {
            Publisher publisher = db.Publishers.Find(login.username);
            if (publisher == null)
            {
                return NotFound();
            }
            else
            {
                if (publisher.password.Equals(login.password))
                {
                    return Ok(publisher);
                }
                else
                {
                    return NotFound();
                }
            }
        }

        /// <summary>
        /// Get all registered Campaign of the Publisher
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [Route("api/Publishers/{id}/Campaigns")]
        public IQueryable<CampaignRegistration> GetCampaignRegistrations(string id)
        {
            IQueryable<CampaignRegistration> result = db.CampaignRegistrations.Where(c => c.publisherID.Equals(id));
            return result;
        }

        // PUT: api/Publishers/5
        //[ResponseType(typeof(void))]
        //public IHttpActionResult PutPublisher(string id, Publisher publisher)
        //{
        //    if (!ModelState.IsValid)
        //    {
        //        return BadRequest(ModelState);
        //    }

        //    if (id != publisher.publisherID)
        //    {
        //        return BadRequest();
        //    }

        //    db.Entry(publisher).State = EntityState.Modified;

        //    try
        //    {
        //        db.SaveChanges();
        //    }
        //    catch (DbUpdateConcurrencyException)
        //    {
        //        if (!PublisherExists(id))
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

        // POST: api/Publishers
        //[ResponseType(typeof(Publisher))]
        //public IHttpActionResult PostPublisher(Publisher publisher)
        //{
        //    if (!ModelState.IsValid)
        //    {
        //        return BadRequest(ModelState);
        //    }

        //    db.Publishers.Add(publisher);

        //    try
        //    {
        //        db.SaveChanges();
        //    }
        //    catch (DbUpdateException)
        //    {
        //        if (PublisherExists(publisher.publisherID))
        //        {
        //            return Conflict();
        //        }
        //        else
        //        {
        //            throw;
        //        }
        //    }

        //    return CreatedAtRoute("DefaultApi", new { id = publisher.publisherID }, publisher);
        //}

        // DELETE: api/Publishers/5
        //[ResponseType(typeof(Publisher))]
        //public IHttpActionResult DeletePublisher(string id)
        //{
        //    Publisher publisher = db.Publishers.Find(id);
        //    if (publisher == null)
        //    {
        //        return NotFound();
        //    }

        //    db.Publishers.Remove(publisher);
        //    db.SaveChanges();

        //    return Ok(publisher);
        //}

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool PublisherExists(string id)
        {
            return db.Publishers.Count(e => e.publisherID == id) > 0;
        }
    }
}