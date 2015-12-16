class PostsController < ApplicationController

skip_before_action :verify_authenticity_token

#returns a list of posts for a specific user using the user's twitter_id
def getMyPosts
	@user = User.where(:twitter_id =>params[:twitter_id]).take
	@posts = @user.posts_on_own_wall.all
	render json: @posts
end

#returns a list of posts for a specific user using the user's id
def getMyPostsByID
	@user = User.where(:id =>params[:id]).take
	@posts = @user.posts_on_own_wall.all
	render json: @posts
end

#add post for a certain user 
def create
  @post = Post.create(post_params)
  if @post.save 
    render json: @post
  else
    render json: @post.errors
  end
end

def post_params
params.require(:post).permit(:owner_id, :profile_id, :text, :image_url)
end

end
