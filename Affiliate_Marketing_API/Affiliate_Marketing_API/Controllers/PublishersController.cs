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
    public class PublishersController : ApiController
    {
        private AffiliateMarketingDBEntities db = new AffiliateMarketingDBEntities();

        //// GET: api/Publishers
        //public IQueryable<Publisher> GetPublishers()
        //{
        //    return db.Publishers;
        //}

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

        [HttpGet]
        [Route("api/Publishers/Login")]
        [ResponseType(typeof(Publisher))]
        public IHttpActionResult LoginPublisher(string id, string password)
        {
            Publisher publisher = db.Publishers.Find(id);
            if (publisher == null)
            {
                return NotFound();
            }
            else
            {
                if (publisher.password.Equals(password))
                {
                    return Ok(publisher);
                }
                else
                {
                    return NotFound();
                }
            }
        }

        // PUT: api/Publishers/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutPublisher(string id, Publisher publisher)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != publisher.publisherID)
            {
                return BadRequest();
            }

            db.Entry(publisher).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PublisherExists(id))
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

        // POST: api/Publishers
        [ResponseType(typeof(Publisher))]
        public IHttpActionResult PostPublisher(Publisher publisher)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Publishers.Add(publisher);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (PublisherExists(publisher.publisherID))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = publisher.publisherID }, publisher);
        }

        // DELETE: api/Publishers/5
        [ResponseType(typeof(Publisher))]
        public IHttpActionResult DeletePublisher(string id)
        {
            Publisher publisher = db.Publishers.Find(id);
            if (publisher == null)
            {
                return NotFound();
            }

            db.Publishers.Remove(publisher);
            db.SaveChanges();

            return Ok(publisher);
        }

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