import instagram_infiltrate
import twitter_infiltrate
import reddit_scrape

mental_illnesses = ['depression', 'anxiety', 'stress', 'suicide', 'bipolar disorder']

# need to take relevant values off a person's account
instagram_handle = instagram_infiltrate.InstagramScrape('k_gymnopedie', 'magnataur')
twitter_handle = twitter_infiltrate.TwitterTarget('ChaotiqueEdge')
reddit_handle = reddit_scrape.RedditTarget()

instagram_results = {}
twitter_results = {}
reddit_results = {}

def write_comments_to_file(filename, lst):
    print()

    if len(lst) == 0:
        print('No comments to write.')
        return

    with open(filename, 'w', encoding='utf-8') as f:
        for comment in lst:
            f.write(comment + '\n')

    print('Wrote {} comments to {}'.format(len(lst), filename))

for mental_illness in mental_illnesses:
    instagram_results[mental_illness] = []
    twitter_results[mental_illness] = []
    reddit_results[mental_illness] = []

    # Scraping instagram information
    ig_result = instagram_handle.search_results(mental_illness)
    handle_feed_lst = []
    for handle in ig_result:
        handle_feed = instagram_handle.get_user_feed(handle['username'])
        handle_feed_lst.append(handle_feed)

    instagram_results[mental_illness] = list(map(lambda x: x['text'], handle_feed_lst))
    filename = 'instagram_' + mental_illness + '.txt'

    write_comments_to_file(filename, instagram_results[mental_illness])

    # Scraping twitter information
    twitter_search = twitter_handle.search_by_parameter(mental_illness)
    twitter_results[mental_illness] = list(map(lambda x: x['text'], twitter_search))
    filename = 'twitter_' + mental_illness + '.txt'

    write_comments_to_file(filename, instagram_results[mental_illness])

    # Scraping reddit information
    reddit_search = reddit_handle.get_posts_info(mental_illness)
    reddit_results[mental_illness] = list(map(lambda x: x['text'], reddit_search))
    filename = 'reddit_' + mental_illness + '.txt'

    write_comments_to_file(filename, instagram_results[mental_illness])
