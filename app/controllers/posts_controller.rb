class PostsController < ApplicationController

#returns a list of posts for a specific user
def getMyPosts
	@user = User.where(:twitter_id =>params[:twitter_id]).take
	@posts = @user.posts_on_own_wall.all
	render json: @posts
end

def getMyPostsByID
	@user = User.where(:id =>params[:id]).take
	@posts = @user.posts_on_own_wall.all
	render json: @posts
end

def AddPost
  @post = Post.new(post_params)
  if @post.save 
    render json: @post
  else
    render json: @post.errors, status: :unprocessable_entity 
  end
end

def post_params
params.require(:post).permit(:owner_id, :profile_id, :text, :image_url)
end

end
